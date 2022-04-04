package com.nkuppan.weatherapp.presentation.weatherdetails

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.ui.viewmodel.BaseAndroidViewModel
import com.nkuppan.weatherapp.domain.extentions.get12HourFormattedTime
import com.nkuppan.weatherapp.domain.extentions.get24HourFormattedTime
import com.nkuppan.weatherapp.domain.extentions.getFormattedDate
import com.nkuppan.weatherapp.domain.model.*
import com.nkuppan.weatherapp.domain.usecase.settings.*
import com.nkuppan.weatherapp.domain.usecase.weather.GetAllWeatherForecastUseCase
import com.nkuppan.weatherapp.presentation.alert.AlertUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    application: Application,
    private val getAllWeatherForecastUseCase: GetAllWeatherForecastUseCase,
    private val getSelectedCityUseCase: GetSelectedCityUseCase,
    private val getTemperatureUseCase: GetTemperatureUseCase,
    private val getPressureUseCase: GetPressureUseCase,
    private val getWindSpeedUseCase: GetWindSpeedUseCase,
    private val getPrecipitationUseCase: GetPrecipitationUseCase,
    private val getDistanceUseCase: GetDistanceUseCase,
    private val getTimeFormatUseCase: GetTimeFormatUseCase,
) : BaseAndroidViewModel(application) {

    private val _allWeatherInfo = MutableSharedFlow<List<WeatherUIAdapterModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val allWeatherInfo = _allWeatherInfo.asSharedFlow()

    private var currentJob: Job? = null

    private var selectedCity: City? = null

    val selectedLocation = MutableLiveData<String>()

    private var temperature = Temperature.CELSIUS
    private var windSpeed = WindSpeed.METERS_PER_SECOND
    private var pressure = Pressure.HECTOPASCAL
    private var precipitation = Precipitation.MILLIMETER
    private var distance = Distance.KILOMETERS
    private var timeFormat = TimeFormat.TWENTY_FOUR_HOUR

    init {
        viewModelScope.launch {
            getSelectedCityUseCase.invoke().collectLatest {
                fetchWeatherInfo(it)
            }
        }
        viewModelScope.launch {
            getTemperatureUseCase.invoke().collectLatest {
                temperature = it
            }
        }
        viewModelScope.launch {
            getWindSpeedUseCase.invoke().collectLatest {
                windSpeed = it
            }
        }
        viewModelScope.launch {
            getPressureUseCase.invoke().collectLatest {
                pressure = it
            }
        }
        viewModelScope.launch {
            getPrecipitationUseCase.invoke().collectLatest {
                precipitation = it
            }
        }
        viewModelScope.launch {
            getDistanceUseCase.invoke().collectLatest {
                distance = it
            }
        }
        viewModelScope.launch {
            getTimeFormatUseCase.invoke().collectLatest {
                timeFormat = it
            }
        }
    }

    fun fetchWeatherInfo() {
        selectedCity?.let {
            fetchWeatherInfo(it)
        }
    }

    private fun fetchWeatherInfo(city: City) {

        if (_isLoading.value == true) {
            currentJob?.cancel()
        }

        _isLoading.value = true

        currentJob = viewModelScope.launch {

            selectedCity = city

            selectedLocation.value = city.getFormattedCityName()

            fetchAllForecastInfo(city)

            _isLoading.value = false
        }
    }

    private suspend fun fetchAllForecastInfo(city: City) {
        when (val response = getAllWeatherForecastUseCase
            .invoke(
                city,
                numberOfHours = NUMBER_OF_HOURS,
                numberOfDays = NUMBER_OF_DAYS
            )
        ) {
            is Resource.Success -> {
                _allWeatherInfo.tryEmit(
                    response.data.map { data ->
                        WeatherUIAdapterModel(
                            data.type.ordinal,
                            data.weather.map { weather ->
                                convertWeatherUIModel(weather)
                            }
                        )
                    }
                )
            }
            is Resource.Error -> {
                _errorMessage.value = (R.string.unable_to_fetch_data)
            }
        }
    }

    private fun convertWeatherUIModel(weather: Weather) =
        WeatherUIModel(
            weather.weatherDayThemeIcon,
            weather.weatherTitle ?: "",
            weather.weatherDescription,
            getTemperature(weather.highTemperature),
            getFeelsLikeTemperature(weather.feelsLikeTemperature),
            getHighLowTemperature(weather.highTemperature, weather.lowTemperature),
            weather.alert?.map {
                AlertUIModel(
                    it.event,
                    "${it.start.getFormattedDate()} - ${it.end.getFormattedDate()}",
                    it.description ?: "",
                    it.senderName
                )
            },
            getWindSpeed(weather.windSpeed),
            getHumidity(weather.humidity),
            getUVIndex(weather.uvIndex),
            getPressure(weather.pressure),
            getVisibility(weather.visibility),
            getDewPoint(weather.dewPoint),
            weather.date.getFormattedDate(),
            getTime(weather.date),
            weather.probability,
            getPrecipitation(weather.precipitation)
        )

    private fun getPrecipitation(precipitation: Double): String {
        return if (precipitation > 0.0) {
            String.format(
                Locale.getDefault(),
                "%s %.1f %s",
                getApplication<Application>().resources.getString(R.string.precipitation_value),
                getPrecipitationValue(precipitation),
                getPrecipitationUnit()
            )
        } else {
            getApplication<Application>().getString(R.string.no_precipitation_within_an_hour)
        }
    }

    private fun getPrecipitationValue(precipitationValue: Double): Double {
        return if (precipitation == Precipitation.MILLIMETER) {
            precipitationValue
        } else {
            precipitationValue.toMillimeterToInches()
        }
    }

    private fun getPrecipitationUnit(): String {
        return if (precipitation == Precipitation.MILLIMETER) {
            getApplication<Application>().resources.getString(R.string.millimeter)
        } else {
            getApplication<Application>().resources.getString(R.string.inches)
        }
    }

    private fun getTime(date: Long): String {
        return if (timeFormat == TimeFormat.TWENTY_FOUR_HOUR) {
            date.get24HourFormattedTime()
        } else {
            date.get12HourFormattedTime()
        }
    }

    private fun getVisibility(visibility: Double): String {
        return String.format(
            Locale.getDefault(),
            "%s %.1f %s",
            getApplication<Application>().resources.getString(R.string.visibility_value),
            getDistanceValue(visibility),
            getDistanceUnit()
        )
    }

    private fun getDistanceValue(distanceValue: Double): Double {
        return if (distance == Distance.KILOMETERS) {
            distanceValue.toKilometer()
        } else {
            distanceValue.toMiles()
        }
    }

    private fun getDistanceUnit(): String {
        return if (distance == Distance.KILOMETERS) {
            getApplication<Application>().resources.getString(R.string.kilometers)
        } else {
            getApplication<Application>().resources.getString(R.string.miles)
        }
    }

    private fun getUVIndex(uvIndex: Double): String {
        return String.format(
            Locale.getDefault(),
            "%s %.1f",
            getApplication<Application>().resources.getString(R.string.uv_index_value),
            uvIndex
        )
    }

    private fun getHumidity(humidity: Int): String {
        return String.format(
            Locale.getDefault(),
            "%s %d %%",
            getApplication<Application>().resources.getString(R.string.humidity_value),
            humidity
        )
    }

    private fun getFeelsLikeTemperature(temperatureValue: Double): String {
        return String.format(
            Locale.getDefault(),
            "%s %s",
            getApplication<Application>().resources.getString(R.string.feels_like_value),
            getTemperature(temperatureValue)
        )
    }

    private fun getDewPoint(temperatureValue: Double): String {
        return String.format(
            Locale.getDefault(),
            "%s %s",
            getApplication<Application>().resources.getString(R.string.dew_point_value),
            getTemperature(temperatureValue)
        )
    }

    private fun getTemperature(temperatureValue: Double): String {
        return String.format(
            Locale.getDefault(),
            "%d %s",
            getTemperatureValue(temperatureValue),
            getTemperatureUnit()
        )
    }

    private fun getHighLowTemperature(highTemperature: Double, lowTemperature: Double): String {
        return String.format(
            Locale.getDefault(),
            "%d/%d %s",
            getTemperatureValue(highTemperature),
            getTemperatureValue(lowTemperature),
            getTemperatureUnit()
        )
    }

    private fun getTemperatureUnit(): String {
        return if (temperature == Temperature.CELSIUS) {
            getApplication<Application>().resources.getString(R.string.celsius)
        } else {
            getApplication<Application>().resources.getString(R.string.fahrenheit)
        }
    }

    private fun getTemperatureValue(pressureValue: Double): Int {
        return if (temperature == Temperature.CELSIUS) {
            pressureValue
        } else {
            pressureValue.toFahrenheit()
        }.roundToInt()
    }

    private fun getWindSpeed(windSpeed: Double): String {
        return String.format(
            Locale.getDefault(),
            "%s %.1f %s",
            getApplication<Application>().resources.getString(R.string.wind_value),
            getWindSpeedValue(windSpeed),
            getWindSpeedUnit()
        )
    }

    private fun getWindSpeedUnit(): String {
        return when (windSpeed) {
            WindSpeed.MILES_PER_HOUR -> {
                getApplication<Application>().resources.getString(R.string.miles_per_hour)
            }
            WindSpeed.KILOMETERS_PER_HOUR -> {
                getApplication<Application>().resources.getString(R.string.kilometers_per_hour)
            }
            else -> {
                getApplication<Application>().resources.getString(R.string.meters_per_second)
            }
        }
    }

    private fun getWindSpeedValue(windSpeedValue: Double): Double {
        return when (windSpeed) {
            WindSpeed.MILES_PER_HOUR -> {
                windSpeedValue
            }
            WindSpeed.KILOMETERS_PER_HOUR -> {
                windSpeedValue.toKilometerPerHour()
            }
            else -> {
                windSpeedValue.toMeterPerSecond()
            }
        }
    }

    private fun getPressure(pressure: Double): String {
        return String.format(
            Locale.getDefault(),
            "%s %.1f %s",
            getApplication<Application>().resources.getString(R.string.wind_value),
            getPressureValue(pressure),
            getPressureUnit()
        )
    }

    private fun getPressureUnit(): String {
        return if (pressure == Pressure.HECTOPASCAL) {
            getApplication<Application>().resources.getString(R.string.hpa)
        } else {
            getApplication<Application>().resources.getString(R.string.inHg)
        }
    }

    private fun getPressureValue(pressureValue: Double): Double {
        return if (pressure == Pressure.HECTOPASCAL) {
            pressureValue
        } else {
            pressureValue.toInchesOfMercury()
        }
    }

    companion object {
        const val NUMBER_OF_HOURS = 12
        const val NUMBER_OF_DAYS = 7
    }
}

private fun Double.toMillimeterToInches(): Double {
    return this / 25.4
}

private fun Double.toFahrenheit(): Double {
    return this * 1.8 + 32
}

private fun Double.toKilometerPerHour(): Double {
    return this * 1.609
}

private fun Double.toMeterPerSecond(): Double {
    return this * 2.237
}

private fun Double.toInchesOfMercury(): Double {
    return this / 33.863886666667
}

private fun Double.toKilometer(): Double {
    return this / 1000
}

private fun Double.toMiles(): Double {
    return this / 1609.34
}

package com.nkuppan.weatherapp.presentation.weatherdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.domain.extentions.get12HourFormattedTime
import com.nkuppan.weatherapp.domain.extentions.get24HourFormattedTime
import com.nkuppan.weatherapp.domain.extentions.getFormattedDate
import com.nkuppan.weatherapp.domain.model.*
import com.nkuppan.weatherapp.domain.usecase.settings.*
import com.nkuppan.weatherapp.domain.usecase.weather.GetAllWeatherForecastUseCase
import com.nkuppan.weatherapp.presentation.alert.AlertUIModel
import com.nkuppan.weatherapp.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val getAllWeatherForecastUseCase: GetAllWeatherForecastUseCase,
    private val getSelectedCityUseCase: GetSelectedCityUseCase,
    private val getTemperatureUseCase: GetTemperatureUseCase,
    private val getPressureUseCase: GetPressureUseCase,
    private val getWindSpeedUseCase: GetWindSpeedUseCase,
    private val getPrecipitationUseCase: GetPrecipitationUseCase,
    private val getDistanceUseCase: GetDistanceUseCase,
    private val getTimeFormatUseCase: GetTimeFormatUseCase,
) : ViewModel() {

    private val _weatherUIState = MutableStateFlow<WeatherUIState>(
        WeatherUIState.Loading(true)
    )
    val weatherUIState = _weatherUIState.asStateFlow()

    private var currentJob: Job? = null

    private var selectedCity: City? = null

    val selectedLocation = MutableStateFlow("")

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

        currentJob?.cancel()

        currentJob = viewModelScope.launch {

            _weatherUIState.value = WeatherUIState.Loading(status = true)

            selectedCity = city

            selectedLocation.value = city.getFormattedCityName()

            fetchAllForecastInfo(city)

            _weatherUIState.value = WeatherUIState.Loading(status = false)
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
                _weatherUIState.value = WeatherUIState.Success(
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
                _weatherUIState.value = WeatherUIState.Error(
                    UiText.StringResource(R.string.unable_to_fetch_data)
                )
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

    private fun getPrecipitation(precipitation: Double): UiText {
        return if (precipitation > 0.0) {
            UiText.StringResource(
                R.string.precipitation_value,
                getPrecipitationValue(precipitation),
                getPrecipitationUnit()
            )
        } else {
            UiText.StringResource(R.string.no_precipitation_within_an_hour)
        }
    }

    private fun getPrecipitationValue(precipitationValue: Double): Double {
        return if (precipitation == Precipitation.MILLIMETER) {
            precipitationValue
        } else {
            precipitationValue.toMillimeterToInches()
        }
    }

    private fun getPrecipitationUnit(): UiText {
        return if (precipitation == Precipitation.MILLIMETER) {
            UiText.StringResource(R.string.millimeter)
        } else {
            UiText.StringResource(R.string.inches)
        }
    }

    private fun getTime(date: Long): String {
        return if (timeFormat == TimeFormat.TWENTY_FOUR_HOUR) {
            date.get24HourFormattedTime()
        } else {
            date.get12HourFormattedTime()
        }
    }

    private fun getVisibility(visibility: Double): UiText {
        return UiText.StringResource(
            R.string.visibility_value,
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

    private fun getDistanceUnit(): UiText {
        return if (distance == Distance.KILOMETERS) {
            UiText.StringResource(R.string.kilometers)
        } else {
            UiText.StringResource(R.string.miles)
        }
    }

    private fun getUVIndex(uvIndex: Double): UiText {
        return UiText.StringResource(
            R.string.uv_index_value,
            uvIndex
        )
    }

    private fun getHumidity(humidity: Int): UiText {
        return UiText.StringResource(R.string.humidity_value, humidity)
    }

    private fun getFeelsLikeTemperature(temperatureValue: Double): UiText {
        return UiText.StringResource(R.string.feels_like_value, getTemperature(temperatureValue))
    }

    private fun getDewPoint(temperatureValue: Double): UiText {
        return UiText.StringResource(R.string.dew_point_value, getTemperature(temperatureValue))
    }

    private fun getTemperature(temperatureValue: Double): UiText {
        return UiText.StringResource(
            R.string.temperature_value,
            getTemperatureValue(temperatureValue),
            getTemperatureUnit()
        )
    }

    private fun getHighLowTemperature(highTemperature: Double, lowTemperature: Double): UiText {
        return UiText.StringResource(
            R.string.high_low_temperature_value,
            getTemperatureValue(highTemperature),
            getTemperatureValue(lowTemperature),
            getTemperatureUnit()
        )
    }

    private fun getTemperatureUnit(): UiText {
        return if (temperature == Temperature.CELSIUS) {
            UiText.StringResource(R.string.celsius)
        } else {
            UiText.StringResource(R.string.fahrenheit)
        }
    }

    private fun getTemperatureValue(pressureValue: Double): Int {
        return if (temperature == Temperature.CELSIUS) {
            pressureValue
        } else {
            pressureValue.toFahrenheit()
        }.roundToInt()
    }

    private fun getWindSpeed(windSpeed: Double): UiText {
        return UiText.StringResource(
            R.string.wind_value,
            getWindSpeedValue(windSpeed),
            getWindSpeedUnit()
        )
    }

    private fun getWindSpeedUnit(): UiText {
        return when (windSpeed) {
            WindSpeed.MILES_PER_HOUR -> {
                UiText.StringResource(R.string.miles_per_hour)
            }
            WindSpeed.KILOMETERS_PER_HOUR -> {
                UiText.StringResource(R.string.kilometers_per_hour)
            }
            else -> {
                UiText.StringResource(R.string.meters_per_second)
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

    private fun getPressure(pressure: Double): UiText {
        return UiText.StringResource(
            R.string.pressure_value,
            getPressureValue(pressure),
            getPressureUnit()
        )
    }

    private fun getPressureUnit(): UiText {
        return if (pressure == Pressure.HECTOPASCAL) {
            UiText.StringResource(R.string.hpa)
        } else {
            UiText.StringResource(R.string.inHg)
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

sealed class WeatherUIState {
    data class Loading(val status: Boolean) : WeatherUIState()
    data class Success(val info: List<WeatherUIAdapterModel>) : WeatherUIState()
    data class Error(val info: UiText) : WeatherUIState()
}

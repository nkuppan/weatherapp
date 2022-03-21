package com.nkuppan.weatherapp.presentation.settings

import androidx.lifecycle.viewModelScope
import com.nkuppan.weatherapp.core.ui.viewmodel.BaseViewModel
import com.nkuppan.weatherapp.domain.model.*
import com.nkuppan.weatherapp.domain.usecase.settings.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveTemperatureUseCase: SaveTemperatureUseCase,
    private val savePressureUseCase: SavePressureUseCase,
    private val savePrecipitationUseCase: SavePrecipitationUseCase,
    private val saveDistanceUseCase: SaveDistanceUseCase,
    private val saveTimeFormatUseCase: SaveTimeFormatUseCase,
    private val saveWindSpeedUseCase: SaveWindSpeedUseCase,
    private val getTemperatureUseCase: GetTemperatureUseCase,
    private val getPressureUseCase: GetPressureUseCase,
    private val getWindSpeedUseCase: GetWindSpeedUseCase,
    private val getPrecipitationUseCase: GetPrecipitationUseCase,
    private val getDistanceUseCase: GetDistanceUseCase,
    private val getTimeFormatUseCase: GetTimeFormatUseCase,
) : BaseViewModel() {

    private val _temperature: MutableStateFlow<Temperature> = MutableStateFlow(Temperature.CELSIUS)
    val temperature = _temperature.asStateFlow()

    private val _windSpeed: MutableStateFlow<WindSpeed> = MutableStateFlow(WindSpeed.MILES_PER_HOUR)
    val windSpeed = _windSpeed.asStateFlow()

    private val _pressure: MutableStateFlow<Pressure> = MutableStateFlow(Pressure.HECTOPASCAL)
    val pressure = _pressure.asStateFlow()

    private val _precipitation: MutableStateFlow<Precipitation> =
        MutableStateFlow(Precipitation.INCHES)
    val precipitation = _precipitation.asStateFlow()

    private val _distance: MutableStateFlow<Distance> = MutableStateFlow(Distance.MILES)
    val distance = _distance.asStateFlow()

    private val _timeFormat: MutableStateFlow<TimeFormat> =
        MutableStateFlow(TimeFormat.TWENTY_FOUR_HOUR)
    val timeFormat = _timeFormat.asStateFlow()

    init {
        viewModelScope.launch {
            getTemperatureUseCase.invoke().collectLatest {
                _temperature.value = it
            }
        }
        viewModelScope.launch {
            getWindSpeedUseCase.invoke().collectLatest {
                _windSpeed.value = it
            }
        }
        viewModelScope.launch {
            getPressureUseCase.invoke().collectLatest {
                _pressure.value = it
            }
        }
        viewModelScope.launch {
            getPrecipitationUseCase.invoke().collectLatest {
                _precipitation.value = it
            }
        }
        viewModelScope.launch {
            getDistanceUseCase.invoke().collectLatest {
                _distance.value = it
            }
        }
        viewModelScope.launch {
            getTimeFormatUseCase.invoke().collectLatest {
                _timeFormat.value = it
            }
        }
    }

    fun saveTemperature(temperature: Temperature) {
        viewModelScope.launch {
            saveTemperatureUseCase.invoke(temperature)
        }
    }

    fun saveWindSpeed(windSpeed: WindSpeed) {
        viewModelScope.launch {
            saveWindSpeedUseCase.invoke(windSpeed)
        }
    }

    fun saveTimeFormat(timeFormat: TimeFormat) {
        viewModelScope.launch {
            saveTimeFormatUseCase.invoke(timeFormat)
        }
    }

    fun savePressure(pressure: Pressure) {
        viewModelScope.launch {
            savePressureUseCase.invoke(pressure)
        }
    }

    fun savePrecipitation(precipitation: Precipitation) {
        viewModelScope.launch {
            savePrecipitationUseCase.invoke(precipitation)
        }
    }

    fun saveDistance(distance: Distance) {
        viewModelScope.launch {
            saveDistanceUseCase.invoke(distance)
        }
    }
}
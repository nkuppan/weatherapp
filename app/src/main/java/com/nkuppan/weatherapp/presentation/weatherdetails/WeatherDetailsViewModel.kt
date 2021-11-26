package com.nkuppan.weatherapp.presentation.weatherdetails

import androidx.lifecycle.viewModelScope
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.ui.viewmodel.BaseViewModel
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.Weather
import com.nkuppan.weatherapp.domain.usecase.GetCityDetailsUseCase
import com.nkuppan.weatherapp.domain.usecase.GetDailyWeatherForecastUseCase
import com.nkuppan.weatherapp.domain.usecase.GetHourlyWeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val cityDetailsUseCase: GetCityDetailsUseCase,
    private val dailyWeatherForecastUseCase: GetDailyWeatherForecastUseCase,
    private val hourlyWeatherForecastUseCase: GetHourlyWeatherForecastUseCase
) : BaseViewModel() {

    private val _dailyForecastInfo: MutableSharedFlow<List<Weather>> = MutableSharedFlow()
    val dailyForecastInfo = _dailyForecastInfo.asSharedFlow()

    private val _hourlyForecastInfo: MutableSharedFlow<List<Weather>> = MutableSharedFlow()
    val hourlyForecastInfo = _hourlyForecastInfo.asSharedFlow()

    fun fetchWeatherInfo(cityName: String) {

        if (_isLoading.value == true) {
            return
        }

        _isLoading.value = true

        viewModelScope.launch {

            when (val response = cityDetailsUseCase.invoke(cityName)) {
                is Resource.Success -> {
                    if (response.data.isNotEmpty()) {
                        findForecastInfo(response.data[0])
                    } else {
                        _errorMessage.emit(R.string.city_name_is_invalid)
                    }
                }
                is Resource.Error -> {
                    _errorMessage.emit(R.string.unable_to_fecth_data)
                }
            }

            _isLoading.value = false
        }
    }

    private suspend fun findForecastInfo(city: City) {

        when (val response =
            hourlyWeatherForecastUseCase.invoke(city, numberOfHours = NUMBER_OF_HOURS)) {
            is Resource.Success -> {
                _hourlyForecastInfo.emit(response.data.forecasts)
            }
            is Resource.Error -> {
                _errorMessage.emit(R.string.unable_to_fecth_data)
            }
        }

        when (val response =
            dailyWeatherForecastUseCase.invoke(city, numberOfDays = NUMBER_OF_DAYS)) {
            is Resource.Success -> {
                _dailyForecastInfo.emit(response.data.forecasts)
            }
            is Resource.Error -> {
                _errorMessage.emit(R.string.unable_to_fecth_data)
            }
        }
    }

    companion object {
        const val NUMBER_OF_HOURS = 12
        const val NUMBER_OF_DAYS = 7
    }
}
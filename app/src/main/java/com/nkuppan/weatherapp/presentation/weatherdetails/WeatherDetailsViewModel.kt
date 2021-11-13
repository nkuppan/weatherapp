package com.nkuppan.weatherapp.presentation.weatherdetails

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.extention.Result
import com.nkuppan.weatherapp.core.ui.viewmodel.BaseViewModel
import com.nkuppan.weatherapp.domain.model.Weather
import com.nkuppan.weatherapp.domain.usecase.GetDailyWeatherForecastUseCase
import com.nkuppan.weatherapp.domain.usecase.GetHourlyWeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val aApplication: Application,
    private val dailyWeatherForecastUseCase: GetDailyWeatherForecastUseCase,
    private val hourlyWeatherForecastUseCase: GetHourlyWeatherForecastUseCase
) : BaseViewModel(aApplication) {

    private val _dailyForecastInfo: MutableSharedFlow<List<Weather>> = MutableSharedFlow()
    val dailyForecastInfo: SharedFlow<List<Weather>> = _dailyForecastInfo.asSharedFlow()

    private val _hourlyForecastInfo: MutableSharedFlow<List<Weather>> = MutableSharedFlow()
    val hourlyForecastInfo: SharedFlow<List<Weather>> = _hourlyForecastInfo.asSharedFlow()

    fun fetchWeatherInfo(cityName: String) {

        if (_isLoading.value == true) {
            return
        }

        _isLoading.value = true

        viewModelScope.launch {

            when (val response = dailyWeatherForecastUseCase.invoke(cityName, numberOfDays = 5)) {
                is Result.Success -> {
                    _dailyForecastInfo.emit(response.data.forecasts)
                }
                is Result.Error -> {
                    _dailyForecastInfo.emit(emptyList())
                    _errorMessage.emit(aApplication.getString(R.string.unable_to_fecth_data))
                }
            }

            when (val response = hourlyWeatherForecastUseCase.invoke(cityName, numberOfHours = 5)) {
                is Result.Success -> {
                    _hourlyForecastInfo.emit(response.data.forecasts)
                }
                is Result.Error -> {
                    _hourlyForecastInfo.emit(emptyList())
                    _errorMessage.emit(aApplication.getString(R.string.unable_to_fecth_data))
                }
            }

            _isLoading.value = false
        }
    }
}
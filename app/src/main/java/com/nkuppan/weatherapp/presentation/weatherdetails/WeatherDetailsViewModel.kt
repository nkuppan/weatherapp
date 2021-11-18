package com.nkuppan.weatherapp.presentation.weatherdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.extention.NetworkResult
import com.nkuppan.weatherapp.core.ui.viewmodel.BaseViewModel
import com.nkuppan.weatherapp.domain.model.Weather
import com.nkuppan.weatherapp.domain.usecase.GetDailyWeatherForecastUseCase
import com.nkuppan.weatherapp.domain.usecase.GetHourlyWeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val dailyWeatherForecastUseCase: GetDailyWeatherForecastUseCase,
    private val hourlyWeatherForecastUseCase: GetHourlyWeatherForecastUseCase
) : BaseViewModel() {

    private val _dailyForecastInfo: MutableLiveData<List<Weather>> = MutableLiveData()
    val dailyForecastInfo: LiveData<List<Weather>> = _dailyForecastInfo

    private val _hourlyForecastInfo: MutableLiveData<List<Weather>> = MutableLiveData()
    val hourlyForecastInfo: LiveData<List<Weather>> = _hourlyForecastInfo

    fun fetchWeatherInfo(cityName: String) {

        if (_isLoading.value == true) {
            return
        }

        _isLoading.value = true

        viewModelScope.launch {

            when (val response =
                dailyWeatherForecastUseCase.invoke(cityName, numberOfDays = NUMBER_OF_DAYS)) {
                is NetworkResult.Success -> {
                    _dailyForecastInfo.value = (response.data.forecasts)
                }
                is NetworkResult.Error -> {
                    _dailyForecastInfo.value = (emptyList())
                    _errorMessage.value = R.string.unable_to_fecth_data
                }
            }

            when (val response =
                hourlyWeatherForecastUseCase.invoke(cityName, numberOfHours = NUMBER_OF_HOURS)) {
                is NetworkResult.Success -> {
                    _hourlyForecastInfo.value = (response.data.forecasts)
                }
                is NetworkResult.Error -> {
                    _hourlyForecastInfo.value = (emptyList())
                    _errorMessage.value = R.string.unable_to_fecth_data
                }
            }

            _isLoading.value = false
        }
    }

    companion object {
        const val NUMBER_OF_HOURS = 12
        const val NUMBER_OF_DAYS = 5
    }
}
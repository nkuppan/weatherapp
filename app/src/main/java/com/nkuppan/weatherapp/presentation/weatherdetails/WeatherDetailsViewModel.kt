package com.nkuppan.weatherapp.presentation.weatherdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.ui.viewmodel.BaseViewModel
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.Weather
import com.nkuppan.weatherapp.domain.model.WeatherUIModel
import com.nkuppan.weatherapp.domain.usecase.GetAllWeatherForecastUseCase
import com.nkuppan.weatherapp.domain.usecase.GetCityDetailsUseCase
import com.nkuppan.weatherapp.domain.usecase.GetDailyWeatherForecastUseCase
import com.nkuppan.weatherapp.domain.usecase.GetHourlyWeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val getCityDetailsUseCase: GetCityDetailsUseCase,
    private val dailyWeatherForecastUseCase: GetDailyWeatherForecastUseCase,
    private val hourlyWeatherForecastUseCase: GetHourlyWeatherForecastUseCase,
    private val getAllWeatherForecastUseCase: GetAllWeatherForecastUseCase
) : BaseViewModel() {

    private val _allWeatherInfo: MutableLiveData<List<WeatherUIModel>> = MutableLiveData()
    val allWeatherInfo: LiveData<List<WeatherUIModel>> = _allWeatherInfo

    private val _dailyForecastInfo: MutableLiveData<List<Weather>> = MutableLiveData()
    val dailyForecastInfo: LiveData<List<Weather>> = _dailyForecastInfo

    private val _hourlyForecastInfo: MutableLiveData<List<Weather>> = MutableLiveData()
    val hourlyForecastInfo: LiveData<List<Weather>> = _hourlyForecastInfo

    fun fetchWeatherInfo(cityName: String, fetchAllDataInOnce: Boolean = false) {

        if (_isLoading.value == true) {
            return
        }

        _isLoading.value = true

        viewModelScope.launch {

            when (val response = getCityDetailsUseCase.invoke(cityName)) {
                is Resource.Success -> {
                    if (response.data.isNotEmpty()) {
                        if (fetchAllDataInOnce) {
                            fetchAllForecastInfo(response.data[0])
                        } else {
                            fetchIndividualForecastInfo(response.data[0])
                        }
                    } else {
                        _errorMessage.value = (R.string.city_name_is_invalid)
                    }
                }
                is Resource.Error -> {
                    _errorMessage.value = (R.string.unable_to_fetch_data)
                }
            }

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
                _allWeatherInfo.value = (response.data)
            }
            is Resource.Error -> {
                _errorMessage.value = (R.string.unable_to_fetch_data)
            }
        }
    }

    private suspend fun fetchIndividualForecastInfo(city: City) {

        when (val response =
            hourlyWeatherForecastUseCase.invoke(city, numberOfHours = NUMBER_OF_HOURS)) {
            is Resource.Success -> {
                _hourlyForecastInfo.value = (response.data.forecasts)
            }
            is Resource.Error -> {
                _errorMessage.value = (R.string.unable_to_fetch_data)
            }
        }

        when (val response =
            dailyWeatherForecastUseCase.invoke(city, numberOfDays = NUMBER_OF_DAYS)) {
            is Resource.Success -> {
                _dailyForecastInfo.value = (response.data.forecasts)
            }
            is Resource.Error -> {
                _errorMessage.value = (R.string.unable_to_fetch_data)
            }
        }
    }

    companion object {
        const val NUMBER_OF_HOURS = 12
        const val NUMBER_OF_DAYS = 7
    }
}
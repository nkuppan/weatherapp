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
import com.nkuppan.weatherapp.domain.usecase.GetDailyWeatherForecastUseCase
import com.nkuppan.weatherapp.domain.usecase.GetHourlyWeatherForecastUseCase
import com.nkuppan.weatherapp.domain.usecase.GetSelectedCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val dailyWeatherForecastUseCase: GetDailyWeatherForecastUseCase,
    private val hourlyWeatherForecastUseCase: GetHourlyWeatherForecastUseCase,
    private val getAllWeatherForecastUseCase: GetAllWeatherForecastUseCase,
    private val getSelectedCityUseCase: GetSelectedCityUseCase
) : BaseViewModel() {

    private val _allWeatherInfo: MutableSharedFlow<List<WeatherUIModel>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val allWeatherInfo: SharedFlow<List<WeatherUIModel>> = _allWeatherInfo.asSharedFlow()

    private val _dailyForecastInfo: MutableLiveData<List<Weather>> = MutableLiveData()
    val dailyForecastInfo: LiveData<List<Weather>> = _dailyForecastInfo

    private val _hourlyForecastInfo: MutableLiveData<List<Weather>> = MutableLiveData()
    val hourlyForecastInfo: LiveData<List<Weather>> = _hourlyForecastInfo

    private var currentJob: Job? = null

    private var selectedCity: City? = null

    val selectedLocation = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            getSelectedCityUseCase.invoke().collectLatest {
                fetchWeatherInfo(it, true)
            }
        }
    }

    fun fetchWeatherInfo(fetchAllDataInOnce: Boolean = false) {
        selectedCity?.let {
            fetchWeatherInfo(it, fetchAllDataInOnce)
        }
    }

    private fun fetchWeatherInfo(city: City, fetchAllDataInOnce: Boolean = false) {

        if (_isLoading.value == true) {
            currentJob?.cancel()
        }

        _isLoading.value = true

        currentJob = viewModelScope.launch {

            selectedCity = city

            selectedLocation.value = city.getFormattedCityName()

            if (fetchAllDataInOnce) {
                fetchAllForecastInfo(city)
            } else {
                fetchIndividualForecastInfo(city)
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
                _allWeatherInfo.tryEmit(response.data)
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
package com.nkuppan.weatherapp.domain.usecase

import com.nkuppan.weatherapp.core.extention.NetworkResult
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class GetDailyWeatherForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        cityName: String,
        numberOfDays: Int
    ): NetworkResult<WeatherForecast> {

        return when (val cityResponse = repository.getAccWeatherCityId(cityName)) {
            is NetworkResult.Success -> {
                if (cityResponse.data.isNotEmpty()) {
                    repository.getDailyWeatherForecast(cityResponse.data[0].key, numberOfDays)
                } else {
                    NetworkResult.Error(IllegalArgumentException("City name not found exception"))
                }
            }
            is NetworkResult.Error -> {
                cityResponse
            }
        }
    }
}
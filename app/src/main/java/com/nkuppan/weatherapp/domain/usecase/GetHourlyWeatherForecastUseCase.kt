package com.nkuppan.weatherapp.domain.usecase

import com.nkuppan.weatherapp.core.extention.NetworkResult
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class GetHourlyWeatherForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        cityName: String,
        numberOfHours: Int
    ): NetworkResult<WeatherForecast> {

        return when (val cityResponse = repository.getAccWeatherCityId(cityName)) {
            is NetworkResult.Success -> {
                if (cityResponse.data.isNotEmpty()) {
                    repository.getHourlyWeatherForecast(cityResponse.data[0].key, numberOfHours)
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
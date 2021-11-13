package com.nkuppan.weatherapp.domain.usecase

import com.nkuppan.weatherapp.core.extention.Result
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class GetHourlyWeatherForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        cityName: String,
        numberOfHours: Int
    ): Result<WeatherForecast> {
        return repository.getHourlyWeatherForecast(cityName, numberOfHours)
    }
}
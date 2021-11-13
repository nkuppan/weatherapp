package com.nkuppan.weatherapp.domain.usecase

import com.nkuppan.weatherapp.core.extention.Result
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class GetDailyWeatherForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        cityName: String,
        numberOfDays:Int
    ): Result<WeatherForecast> {
        return repository.getDailyWeatherForecast(cityName, numberOfDays)
    }
}
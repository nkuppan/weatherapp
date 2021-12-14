package com.nkuppan.weatherapp.domain.usecase

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class GetDailyWeatherForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        city: City,
        numberOfDays: Int
    ): Resource<WeatherForecast> {

        if (!city.isValidCity()) {
            return Resource.Error(KotlinNullPointerException("Invalid city or information"))
        }

        return repository.getDailyWeatherForecast(city, numberOfDays)
    }
}
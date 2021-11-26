package com.nkuppan.weatherapp.domain.usecase

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class GetCityDetailsUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        cityName: String
    ): Resource<List<City>> {

        if (cityName.isBlank()) {
            return Resource.Error(KotlinNullPointerException("Invalid city name or information"))
        }

        return repository.getCityInfo(cityName)
    }
}
package com.nkuppan.weatherapp.domain.usecase.favorite

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.respository.FavoriteCityRepository
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class GetAllFavoriteCitiesUseCase(private val repository: FavoriteCityRepository) {

    suspend operator fun invoke(): Resource<List<City>> {
        return repository.getAllFavoriteCities()
    }
}
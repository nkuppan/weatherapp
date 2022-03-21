package com.nkuppan.weatherapp.domain.usecase.favorite

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.respository.FavoriteCityRepository

class SaveFavoriteCityUseCase(private val cityRepository: FavoriteCityRepository) {

    suspend operator fun invoke(city: City) {
        cityRepository.addFavoriteCity(city)
    }
}
package com.nkuppan.weatherapp.data.repository

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.respository.FavoriteCityRepository

class FakeFavoriteCityRepository : FavoriteCityRepository {

    private val favoriteCities = mutableListOf<City>()

    override suspend fun addFavoriteCity(favoriteCity: City) {
        if (!favoriteCities.any { it.name == favoriteCity.name }) {
            favoriteCities.add(favoriteCity)
        }
    }

    override suspend fun getAllFavoriteCities(): Resource<List<City>> {
        return Resource.Success(favoriteCities)
    }
}
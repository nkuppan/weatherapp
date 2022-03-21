package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource

interface FavoriteCityRepository {

    /**
     * Storing the favorites
     */
    suspend fun addFavoriteCity(favoriteCity: City)

    /**
     * @return all the available favorites
     */
    suspend fun getAllFavoriteCities(): Resource<List<City>>
}
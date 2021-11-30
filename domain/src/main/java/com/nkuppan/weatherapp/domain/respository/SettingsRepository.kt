package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.domain.model.City
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    /**
     * Storing the selected country
     */
    suspend fun saveCountry(city: City)

    /**
     * Reading the selected country
     */
    fun getSelectedCountry(): Flow<City>
}
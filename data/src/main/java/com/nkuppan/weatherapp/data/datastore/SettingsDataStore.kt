package com.nkuppan.weatherapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nkuppan.weatherapp.data.extension.jsonStringToObject
import com.nkuppan.weatherapp.data.extension.objectToJsonString
import com.nkuppan.weatherapp.domain.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val dataStore: DataStore<Preferences>) {

    suspend fun setCountry(mode: City) = dataStore.edit { preferences ->
        preferences[KEY_SELECTED_COUNTRY] = mode.objectToJsonString()
    }

    fun getCountry(): Flow<City> = dataStore.data.map { preferences ->
        (preferences[KEY_SELECTED_COUNTRY] ?: "").jsonStringToObject() ?: City(
            name = "London",
            country = "United Kingdom",
            latitude = 51.5072,
            longitude = 0.1276,
            key = "1",
            rank = 1,
            isFavorite = true
        )
    }

    companion object {
        private val KEY_SELECTED_COUNTRY = stringPreferencesKey("selected_country")
    }
}

package com.nkuppan.weatherapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nkuppan.weatherapp.data.extention.jsonStringToObject
import com.nkuppan.weatherapp.data.extention.objectToJsonString
import com.nkuppan.weatherapp.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val dataStore: DataStore<Preferences>) {

    suspend fun setSelectedPlace(mode: City) = dataStore.edit { preferences ->
        preferences[KEY_SELECTED_COUNTRY] = mode.objectToJsonString()
    }

    fun getSelectedPlace(): Flow<City> = dataStore.data.map { preferences ->
        (preferences[KEY_SELECTED_COUNTRY] ?: "").jsonStringToObject() ?: DEFAULT_CITY
    }

    suspend fun setTemperature(temperature: Temperature) = dataStore.edit { preferences ->
        preferences[KEY_SELECTED_TEMPERATURE] = temperature.ordinal
    }

    fun getTemperature() = dataStore.data.map { preferences ->
        Temperature.values()[preferences[KEY_SELECTED_TEMPERATURE] ?: Temperature.CELSIUS.ordinal]
    }

    suspend fun setWindSpeed(windSpeed: WindSpeed) = dataStore.edit { preferences ->
        preferences[KEY_SELECTED_WIND_SPEED] = windSpeed.ordinal
    }

    fun getWindSpeed() = dataStore.data.map { preferences ->
        WindSpeed.values()[preferences[KEY_SELECTED_WIND_SPEED]
            ?: WindSpeed.METERS_PER_SECOND.ordinal]
    }

    suspend fun setPressure(pressure: Pressure) = dataStore.edit { preferences ->
        preferences[KEY_SELECTED_PRESSURE] = pressure.ordinal
    }

    fun getPressure() = dataStore.data.map { preferences ->
        Pressure.values()[preferences[KEY_SELECTED_PRESSURE] ?: Pressure.HECTOPASCAL.ordinal]
    }

    suspend fun setPrecipitation(precipitation: Precipitation) = dataStore.edit { preferences ->
        preferences[KEY_SELECTED_PRECIPITATION] = precipitation.ordinal
    }

    fun getPrecipitation() = dataStore.data.map { preferences ->
        Precipitation.values()[preferences[KEY_SELECTED_PRECIPITATION]
            ?: Precipitation.MILLIMETER.ordinal]
    }

    suspend fun setDistance(distance: Distance) = dataStore.edit { preferences ->
        preferences[KEY_SELECTED_DISTANCE] = distance.ordinal
    }

    fun getDistance() = dataStore.data.map { preferences ->
        Distance.values()[preferences[KEY_SELECTED_DISTANCE] ?: Distance.KILOMETERS.ordinal]
    }

    suspend fun setTimeFormat(timeFormat: TimeFormat) = dataStore.edit { preferences ->
        preferences[KEY_SELECTED_TIME_FORMAT] = timeFormat.ordinal
    }

    fun getTimeFormat() = dataStore.data.map { preferences ->
        TimeFormat.values()[preferences[KEY_SELECTED_TIME_FORMAT]
            ?: TimeFormat.TWENTY_FOUR_HOUR.ordinal]
    }

    companion object {
        private val KEY_SELECTED_COUNTRY = stringPreferencesKey("selected_country")

        private val KEY_SELECTED_TEMPERATURE = intPreferencesKey("selected_temperature")
        private val KEY_SELECTED_WIND_SPEED = intPreferencesKey("selected_wind_speed")
        private val KEY_SELECTED_PRESSURE = intPreferencesKey("selected_pressure")
        private val KEY_SELECTED_PRECIPITATION = intPreferencesKey("selected_precipitation")
        private val KEY_SELECTED_DISTANCE = intPreferencesKey("selected_distance")
        private val KEY_SELECTED_TIME_FORMAT = intPreferencesKey("selected_time_format")

        private val DEFAULT_CITY = City(
            name = "London",
            country = "United Kingdom",
            latitude = 51.5072,
            longitude = 0.1276,
            key = "1",
            rank = 1,
            isFavorite = true
        )
    }
}

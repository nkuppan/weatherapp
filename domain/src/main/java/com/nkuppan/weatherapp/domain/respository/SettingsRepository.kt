package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.domain.model.*
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    /**
     * Storing the selected country
     */
    suspend fun saveSelectedPlace(city: City): Boolean

    /**
     * Reading the selected country
     */
    fun getSelectedCountry(): Flow<City>

    /**
     * @param temperature to be saved in data store
     */
    suspend fun saveTemperature(temperature: Temperature): Boolean

    /**
     * Retrieved temperature value from data store
     */
    fun getTemperature(): Flow<Temperature>

    /**
     * @param windSpeed to be saved in data store
     */
    suspend fun saveWindSpeed(windSpeed: WindSpeed): Boolean

    /**
     * Retrieved wind speed value from data store
     */
    fun getWindSpeed(): Flow<WindSpeed>

    /**
     * @param pressure to be saved in data store
     */
    suspend fun savePressure(pressure: Pressure): Boolean

    /**
     * Retrieved temperature value from data store
     */
    fun getPressure(): Flow<Pressure>

    /**
     * @param precipitation to be saved in data store
     */
    suspend fun savePrecipitation(precipitation: Precipitation): Boolean

    /**
     * Retrieved precipitation value from data store
     */
    fun getPrecipitation(): Flow<Precipitation>

    /**
     * @param distance to be saved in data store
     */
    suspend fun saveDistance(distance: Distance): Boolean

    /**
     * Retrieved temperature value from data store
     */
    fun getDistance(): Flow<Distance>

    /**
     * @param timeFormat to be saved in data store
     */
    suspend fun saveTimeFormat(timeFormat: TimeFormat): Boolean

    /**
     * Retrieved temperature value from data store
     */
    fun getTimeFormat(): Flow<TimeFormat>
}
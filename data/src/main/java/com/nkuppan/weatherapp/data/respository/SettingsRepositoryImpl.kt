package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.data.datastore.SettingsDataStore
import com.nkuppan.weatherapp.domain.model.*
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import com.nkuppan.weatherapp.domain.utils.AppCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore,
    private val dispatcher: AppCoroutineDispatchers
) : SettingsRepository {

    override suspend fun saveSelectedPlace(city: City): Boolean = withContext(dispatcher.io) {
        dataStore.setSelectedPlace(city)
        return@withContext true
    }

    override fun getSelectedCountry(): Flow<City> {
        return dataStore.getSelectedPlace()
    }

    override suspend fun saveTemperature(temperature: Temperature): Boolean =
        withContext(dispatcher.io) {
            dataStore.setTemperature(temperature)
            return@withContext true
        }

    override fun getTemperature(): Flow<Temperature> {
        return dataStore.getTemperature()
    }

    override suspend fun saveWindSpeed(windSpeed: WindSpeed): Boolean =
        withContext(dispatcher.io) {
            dataStore.setWindSpeed(windSpeed)
            return@withContext true
        }

    override fun getWindSpeed(): Flow<WindSpeed> {
        return dataStore.getWindSpeed()
    }

    override suspend fun savePressure(pressure: Pressure): Boolean =
        withContext(dispatcher.io) {
            dataStore.setPressure(pressure)
            return@withContext true
        }

    override fun getPressure(): Flow<Pressure> {
        return dataStore.getPressure()
    }

    override suspend fun savePrecipitation(precipitation: Precipitation): Boolean =
        withContext(dispatcher.io) {
            dataStore.setPrecipitation(precipitation)
            return@withContext true
        }

    override fun getPrecipitation(): Flow<Precipitation> {
        return dataStore.getPrecipitation()
    }

    override suspend fun saveDistance(distance: Distance): Boolean =
        withContext(dispatcher.io) {
            dataStore.setDistance(distance)
            return@withContext true
        }

    override fun getDistance(): Flow<Distance> {
        return dataStore.getDistance()
    }

    override suspend fun saveTimeFormat(timeFormat: TimeFormat): Boolean =
        withContext(dispatcher.io) {
            dataStore.setTimeFormat(timeFormat)
            return@withContext true
        }

    override fun getTimeFormat(): Flow<TimeFormat> {
        return dataStore.getTimeFormat()
    }
}

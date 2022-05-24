package com.nkuppan.weatherapp.data.repository

import com.nkuppan.weatherapp.domain.model.*
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSettingsRepository : SettingsRepository {

    private var selectedCity = City(
        "London",
        "1",
        1,
        "England",
        null,
        0.0,
        0.0,
        isFavorite = true
    )

    private var temperature = Temperature.CELSIUS
    private var windSpeed = WindSpeed.METERS_PER_SECOND
    private var pressure = Pressure.HECTOPASCAL
    private var precipitation = Precipitation.MILLIMETER
    private var distance = Distance.KILOMETERS
    private var timeFormat = TimeFormat.TWENTY_FOUR_HOUR


    override suspend fun saveSelectedPlace(city: City): Boolean {
        selectedCity = city
        return true
    }

    override fun getSelectedCountry(): Flow<City> = flow {
        selectedCity
    }

    override suspend fun saveTemperature(temperature: Temperature): Boolean {
        this.temperature = temperature
        return true
    }

    override fun getTemperature(): Flow<Temperature> = flow {
        temperature
    }

    override suspend fun saveWindSpeed(windSpeed: WindSpeed): Boolean {
        this.windSpeed = windSpeed
        return true
    }

    override fun getWindSpeed(): Flow<WindSpeed> = flow {
        windSpeed
    }

    override suspend fun savePressure(pressure: Pressure): Boolean {
        this.pressure = pressure
        return true
    }

    override fun getPressure(): Flow<Pressure> = flow {
        pressure
    }

    override suspend fun savePrecipitation(precipitation: Precipitation): Boolean {
        this.precipitation = precipitation
        return true
    }

    override fun getPrecipitation(): Flow<Precipitation> = flow {
        precipitation
    }

    override suspend fun saveDistance(distance: Distance): Boolean {
        this.distance = distance
        return true
    }

    override fun getDistance(): Flow<Distance> = flow {
        distance
    }

    override suspend fun saveTimeFormat(timeFormat: TimeFormat): Boolean {
        this.timeFormat = timeFormat
        return true
    }

    override fun getTimeFormat(): Flow<TimeFormat> = flow {
        timeFormat
    }
}
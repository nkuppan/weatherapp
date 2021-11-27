package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.domain.model.*

interface WeatherRepository {

    /**
     * Getting city information using city name
     *
     * @param cityName city name
     * @return sending list of available city details
     */
    suspend fun getCityInfo(cityName: String): Resource<List<City>>

    /**
     * Read hourly weather forecast information from data source
     */
    suspend fun getHourlyWeatherForecast(city: City, numberOfHours: Int): Resource<WeatherForecast>

    /**
     * Read daily weather forecast information from data source
     */
    suspend fun getDailyWeatherForecast(city: City, numberOfDays: Int): Resource<WeatherForecast>

    /**
     * Read all weather forecast information from data source
     */
    suspend fun getAllWeatherForecast(
        city: City,
        numberOfHours: Int,
        numberOfDays: Int
    ): Resource<Map<WeatherType, List<Weather>>>
}
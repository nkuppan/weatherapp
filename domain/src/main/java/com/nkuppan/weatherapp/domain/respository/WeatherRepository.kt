package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.WeatherForecast

interface WeatherRepository {

    suspend fun getCityInfo(
        cityName: String
    ): Resource<List<City>>

    suspend fun getHourlyWeatherForecast(
        city: City,
        numberOfHours: Int
    ): Resource<WeatherForecast>

    suspend fun getDailyWeatherForecast(
        city: City,
        numberOfDays: Int
    ): Resource<WeatherForecast>
}
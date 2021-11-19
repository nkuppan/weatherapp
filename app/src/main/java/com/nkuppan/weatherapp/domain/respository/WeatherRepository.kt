package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.core.extention.NetworkResult
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.WeatherForecast

interface WeatherRepository {

    suspend fun getCityInfo(
        cityName: String
    ): NetworkResult<List<City>>

    suspend fun getHourlyWeatherForecast(
        city: City,
        numberOfHours: Int
    ): NetworkResult<WeatherForecast>

    suspend fun getDailyWeatherForecast(
        city: City,
        numberOfDays: Int
    ): NetworkResult<WeatherForecast>
}
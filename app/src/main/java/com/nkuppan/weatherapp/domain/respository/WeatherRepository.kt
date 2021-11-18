package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.core.extention.NetworkResult
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.WeatherForecast

interface WeatherRepository {

    suspend fun getAccWeatherCityId(
        cityName: String
    ): NetworkResult<List<City>>

    suspend fun getHourlyWeatherForecast(
        cityId: String,
        numberOfHours: Int
    ): NetworkResult<WeatherForecast>

    suspend fun getDailyWeatherForecast(
        cityId: String,
        numberOfDays: Int
    ): NetworkResult<WeatherForecast>
}
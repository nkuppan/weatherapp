package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.core.extention.Result
import com.nkuppan.weatherapp.domain.model.WeatherForecast

interface WeatherRepository {

    suspend fun getHourlyWeatherForecast(
        cityName: String,
        numberOfHours: Int
    ): Result<WeatherForecast>

    suspend fun getDailyWeatherForecast(
        cityName: String,
        numberOfDays: Int
    ): Result<WeatherForecast>
}
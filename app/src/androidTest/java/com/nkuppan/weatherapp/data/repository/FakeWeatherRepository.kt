package com.nkuppan.weatherapp.data.repository

import com.nkuppan.weatherapp.domain.model.*
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class FakeWeatherRepository: WeatherRepository {
    override suspend fun getCityInfo(cityName: String): Resource<List<City>> {
        TODO("Not yet implemented")
    }

    override suspend fun getHourlyWeatherForecast(
        city: City,
        numberOfHours: Int
    ): Resource<WeatherForecast> {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyWeatherForecast(
        city: City,
        numberOfDays: Int
    ): Resource<WeatherForecast> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllWeatherForecast(
        city: City,
        numberOfHours: Int,
        numberOfDays: Int
    ): Resource<Map<WeatherType, List<Weather>>> {
        TODO("Not yet implemented")
    }
}
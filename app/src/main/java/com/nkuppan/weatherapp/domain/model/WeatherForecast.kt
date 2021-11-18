package com.nkuppan.weatherapp.domain.model

import com.nkuppan.weatherapp.core.extention.getFormattedDate
import com.nkuppan.weatherapp.core.extention.getFormattedTime

data class City(
    val name: String,
    val key: String,
    val rank: Int,
    val country: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

data class WeatherForecast(
    val headlines: String,
    val forecasts: List<Weather>
)

data class Weather(
    val date: Long,
    val probability: String,
    val lowTemperature: Double,
    val highTemperature: Double,
    val weatherDayThemeIcon: String,
    val weatherNightThemeIcon: String,
    val weatherDescription: String
) {
    fun getFormattedTemperature(): String {
        return "$highTemperature °"
    }

    fun getFormattedTime(): String {
        return date.getFormattedTime()
    }

    fun getFormattedDate(): String {
        return date.getFormattedDate()
    }
}
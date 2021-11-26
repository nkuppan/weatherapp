package com.nkuppan.weatherapp.domain.model

import com.nkuppan.weatherapp.domain.extentions.getFormattedDate
import com.nkuppan.weatherapp.domain.extentions.getFormattedTime


data class City(
    val name: String,
    val key: String,
    val rank: Int,
    val country: String,
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    fun isValidCity(): Boolean {
        return latitude != null && longitude != null
    }
}

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
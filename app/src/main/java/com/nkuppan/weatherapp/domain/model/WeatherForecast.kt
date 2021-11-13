package com.nkuppan.weatherapp.domain.model

import com.nkuppan.weatherapp.extension.getFormattedDate
import com.nkuppan.weatherapp.extension.getFormattedTime

data class WeatherForecast(
    val cityName: String,
    val count: Int, // May have number of days or hours,
    val forecasts: List<Weather>
)

data class Weather(
    val date: Long,
    val probability: Double,
    val temperature: Double,
    val weatherIcon: String,
    val weatherDescription: String
) {
    fun getFormattedTemperature(): String {
        return "$temperature °"
    }

    fun getFormattedProbability(): String {
        return "$probability %"
    }

    fun getFormattedTime(): String {
        return date.getFormattedTime()
    }

    fun getFormattedDate(): String {
        return date.getFormattedDate()
    }
}
package com.nkuppan.weatherapp.domain.model

import com.nkuppan.weatherapp.domain.extentions.getFormattedDate
import com.nkuppan.weatherapp.domain.extentions.getFormattedTime


data class City(
    val name: String,
    val key: String,
    val rank: Int,
    val country: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isFavorite: Boolean = false
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
    val weatherDescription: String,
    val weatherTitle: String? = null,
    val alert: String? = null,
    val feelsLikeTemperature: Double = 0.0,
) {
    /**
     * This we have to improve by using android resource string rather than hard coding here
     */
    fun getFormattedFeelsLikeTemperature(): String {
        return "Feels like $feelsLikeTemperature°C"
    }

    fun getFormattedTemperature(): String {
        return "$highTemperature°C"
    }

    fun getFormattedHighLowTemperature(): String {
        return "$highTemperature / ${lowTemperature}°C"
    }

    fun getFormattedTime(): String {
        return date.getFormattedTime()
    }

    fun getFormattedDate(): String {
        return date.getFormattedDate()
    }
}

data class WeatherUIModel(
    val type: WeatherType,
    val weather: List<Weather>
)
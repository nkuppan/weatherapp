package com.nkuppan.weatherapp.domain.model

import com.nkuppan.weatherapp.domain.extentions.getFormattedDate


data class City(
    val name: String,
    val key: String,
    val rank: Int,
    val country: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    var isFavorite: Boolean = false
) {
    fun isValidCity(): Boolean {
        return latitude != null && longitude != null
    }

    fun getFormattedCityName(): String {
        return "${name}, $country"
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
    val alert: List<Alert>? = null,
    val feelsLikeTemperature: Double = 0.0,
    val windSpeed: Double = 0.0,
    val humidity: Int = 0,
    val uvIndex: Double = 0.0,
    val pressure: Double = 0.0,
    val visibility: Double = 0.0,
    val dewPoint: Double = 0.0,
    val precipitation: Double = 0.0,
)

data class WeatherTypeModel(
    val type: WeatherType,
    val weather: List<Weather>
)
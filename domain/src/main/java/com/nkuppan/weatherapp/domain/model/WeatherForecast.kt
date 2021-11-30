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
    val alert: String? = null,
    val feelsLikeTemperature: Double = 0.0,
    val windSpeed: Double = 0.0,
    val humidity: Int = 0,
    val uvIndex: Double = 0.0,
    val pressure: Int = 0,
    val visibility: Int = 0,
    val dewPoint: Double = 0.0,
) {
    /**
     * This we have to improve by using android resource string rather than hard coding here
     */
    fun getFormattedFeelsLikeTemperature(): String {
        return "Feels like ${feelsLikeTemperature.toInt()}°C"
    }

    fun getFormattedTemperature(): String {
        return "${highTemperature.toInt()}°C"
    }

    fun getFormattedHighLowTemperature(): String {
        return "${highTemperature.toInt()} / ${lowTemperature.toInt()}°C"
    }

    fun getFormattedTime(): String {
        return date.getFormattedTime()
    }

    fun getFormattedDate(): String {
        return date.getFormattedDate()
    }

    fun getFormattedWindSpeed(): String {
        return "Wind: $windSpeed m/s"
    }

    fun getFormattedHumidity(): String {
        return "Humidity: $humidity %"
    }

    fun getFormattedUVIndex(): String {
        return "UV Index: $uvIndex"
    }

    fun getFormattedPressure(): String {
        return "Pressure: $pressure hPa"
    }

    fun getFormattedVisibility(): String {
        return "Visibility: ${visibility / 1000} km"
    }

    fun getFormattedDewPoint(): String {
        return "Dew Point: ${dewPoint.toInt()}°C"
    }
}

data class WeatherUIModel(
    val type: WeatherType,
    val weather: List<Weather>
)
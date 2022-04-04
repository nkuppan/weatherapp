package com.nkuppan.weatherapp.presentation.weatherdetails

import com.nkuppan.weatherapp.presentation.alert.AlertUIModel

data class WeatherUIModel(
    val weatherIconURL: String,
    val weatherTitle: String,
    val weatherDescription: String,
    val temperature: String,
    val feelsLikeTemperature: String,
    val highLowTemperature: String,
    val alert: List<AlertUIModel>? = null,
    val windSpeed: String,
    val humidity: String,
    val uvIndex: String,
    val pressure: String,
    val visibility: String,
    val dewPoint: String,
    val date: String,
    val time: String,
    val probability: String,
    val precipitation: String
) {
    fun getAlertMessage(): String {
        return alert?.get(0)?.title ?: ""
    }
}

data class WeatherUIAdapterModel(
    val type: Int,
    val weather: List<WeatherUIModel>
)
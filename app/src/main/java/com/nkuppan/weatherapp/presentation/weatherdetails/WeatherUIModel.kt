package com.nkuppan.weatherapp.presentation.weatherdetails

import com.nkuppan.weatherapp.utils.UiText
import com.nkuppan.weatherapp.presentation.alert.AlertUIModel

data class WeatherUIModel(
    val weatherIconURL: String,
    val weatherTitle: String,
    val weatherDescription: String,
    val temperature: UiText,
    val feelsLikeTemperature: UiText,
    val highLowTemperature: UiText,
    val alert: List<AlertUIModel>? = null,
    val windSpeed: UiText,
    val humidity: UiText,
    val uvIndex: UiText,
    val pressure: UiText,
    val visibility: UiText,
    val dewPoint: UiText,
    val date: String,
    val time: String,
    val probability: String,
    val precipitation: UiText
) {
    fun getAlertMessage(): String {
        return alert?.get(0)?.title ?: ""
    }
}

data class WeatherUIAdapterModel(
    val type: Int,
    val weather: List<WeatherUIModel>
)
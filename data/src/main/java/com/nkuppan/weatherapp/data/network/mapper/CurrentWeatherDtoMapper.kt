package com.nkuppan.weatherapp.data.network.mapper

import com.nkuppan.weatherapp.data.network.response.OpenWeatherMapApiResponse
import com.nkuppan.weatherapp.domain.mapper.DomainMapper
import com.nkuppan.weatherapp.domain.model.Weather

class CurrentWeatherDtoMapper : DomainMapper<OpenWeatherMapApiResponse, List<Weather>> {

    override fun dtoToDomainModel(dtoObject: OpenWeatherMapApiResponse): List<Weather> {

        val currentWeather = dtoObject.currentWeather

        return listOf(
            Weather(
                currentWeather.date,
                "${currentWeather.pop * 100} %",
                currentWeather.temperature,
                currentWeather.temperature,
                currentWeather.weatherImages[0].getDayThemeImageURL(),
                currentWeather.weatherImages[0].getNightThemeImageURL(),
                currentWeather.weatherImages[0].description,
                currentWeather.weatherImages[0].main,
                feelsLikeTemperature = currentWeather.feelsLikeTemperature,
                alert = dtoObject.alerts?.get(0)?.event,
                visibility = currentWeather.visibility,
                dewPoint = currentWeather.dewPoint,
                uvIndex = currentWeather.uvIndex,
                pressure = currentWeather.pressure,
                humidity = currentWeather.humidity,
                windSpeed = currentWeather.windSpeed
            )
        )
    }
}
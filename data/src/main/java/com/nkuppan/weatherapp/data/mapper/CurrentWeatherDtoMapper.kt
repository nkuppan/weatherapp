package com.nkuppan.weatherapp.data.mapper

import com.nkuppan.weatherapp.data.network.response.OpenWeatherMapApiResponse
import com.nkuppan.weatherapp.domain.mapper.Mapper
import com.nkuppan.weatherapp.domain.model.Alert
import com.nkuppan.weatherapp.domain.model.Weather
import javax.inject.Inject

class CurrentWeatherDtoMapper @Inject constructor() :
    Mapper<OpenWeatherMapApiResponse, List<Weather>> {

    override fun convert(fromObject: OpenWeatherMapApiResponse): List<Weather> {

        val currentWeather = fromObject.currentWeather

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
                alert = fromObject.alerts?.map {
                    Alert(
                        it.senderName ?: "",
                        it.start ?: 0,
                        it.end ?: 0,
                        it.event ?: "",
                        it.description,
                        it.tags,
                    )
                },
                visibility = currentWeather.visibility.toDouble(),
                dewPoint = currentWeather.dewPoint,
                uvIndex = currentWeather.uvIndex,
                pressure = currentWeather.pressure.toDouble(),
                humidity = currentWeather.humidity,
                windSpeed = currentWeather.windSpeed,
                precipitation = currentWeather.rain?.get("1h")?.asDouble ?: 0.0
            )
        )
    }
}
package com.nkuppan.weatherapp.data.network.model

import com.nkuppan.weatherapp.data.network.AccWeatherApiService
import com.nkuppan.weatherapp.data.network.response.WeatherForecastHourlyApiResponse
import com.nkuppan.weatherapp.domain.mapper.DomainMapper
import com.nkuppan.weatherapp.domain.model.Weather
import java.text.DecimalFormat

class HourlyWeatherDtoMapper : DomainMapper<WeatherForecastHourlyApiResponse, Weather> {

    override fun mapToDomainModel(model: WeatherForecastHourlyApiResponse): Weather {

        val dayThemeImageURL = String.format(
            AccWeatherApiService.BASE_IMAGE_URL,
            DecimalFormat("00").format(model.weatherIcon)
        )

        val probabilityPercent =
            if (model.hasPrecipitation) "${model.precipitationProbability} %" else "N/A"

        return Weather(
            model.epocTime,
            probabilityPercent,
            model.temperature.value,
            model.temperature.value,
            dayThemeImageURL,
            dayThemeImageURL,
            model.iconStatus
        )
    }

    override fun mapFromDomainModel(domainModel: Weather): WeatherForecastHourlyApiResponse {
        TODO("Not yet implemented")
    }

    fun toDomainList(initial: List<WeatherForecastHourlyApiResponse>): List<Weather> {
        return initial.map { mapToDomainModel(it) }
    }
}
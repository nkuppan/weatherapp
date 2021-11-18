package com.nkuppan.weatherapp.data.network.model

import com.nkuppan.weatherapp.data.network.AccWeatherApiService
import com.nkuppan.weatherapp.data.network.response.WeatherForecastDailyApiResponse
import com.nkuppan.weatherapp.domain.mapper.DomainMapper
import com.nkuppan.weatherapp.domain.model.Weather
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import java.text.DecimalFormat

class DailyWeatherDtoMapper : DomainMapper<WeatherForecastDailyApiResponse, WeatherForecast> {

    override fun mapToDomainModel(model: WeatherForecastDailyApiResponse): WeatherForecast {
        val forecastList = mutableListOf<Weather>()
        if (model.forecastList.isNotEmpty()) {
            model.forecastList.forEach {

                val dayThemeImageURL = String.format(
                    AccWeatherApiService.BASE_IMAGE_URL,
                    DecimalFormat("00").format(it.dayThemeIcon.icon)
                )

                val nightThemeImageURL = String.format(
                    AccWeatherApiService.BASE_IMAGE_URL,
                    DecimalFormat("00").format(it.nightThemeIcon.icon)
                )

                val probabilityPercent =
                    if (it.dayThemeIcon.hasPrecipitation) "${it.dayThemeIcon.precipitationProbability} %" else "N/A"

                forecastList.add(
                    Weather(
                        it.dateTimeInMilliseconds,
                        probabilityPercent,
                        it.temperature.minimum.value,
                        it.temperature.maximum.value,
                        dayThemeImageURL,
                        nightThemeImageURL,
                        it.dayThemeIcon.description
                    )
                )
            }
        }
        return WeatherForecast(
            headlines = model.headline.status,
            forecasts = forecastList
        )
    }

    override fun mapFromDomainModel(domainModel: WeatherForecast): WeatherForecastDailyApiResponse {
        TODO("Not yet implemented")
    }
}
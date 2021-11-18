package com.nkuppan.weatherapp.data.network.model

import com.nkuppan.weatherapp.data.network.OpenWeatherMapApiService
import com.nkuppan.weatherapp.data.network.response.OpenWeatherMapApiResponse
import com.nkuppan.weatherapp.domain.mapper.DomainMapper
import com.nkuppan.weatherapp.domain.model.Weather
import com.nkuppan.weatherapp.domain.model.WeatherForecast

class OpenWeatherDtoMapper : DomainMapper<OpenWeatherMapApiResponse, WeatherForecast> {

    override fun mapToDomainModel(model: OpenWeatherMapApiResponse): WeatherForecast {
        val forecastList = mutableListOf<Weather>()
        if (model.dailyWeather?.isNotEmpty() == true) {
            model.dailyWeather.forEach {

                val dayThemeImageURL =
                    String.format(OpenWeatherMapApiService.BASE_IMAGE_URL, it.weatherImages[0].icon)

                val nightThemeImageURL =
                    String.format(OpenWeatherMapApiService.BASE_IMAGE_URL, it.weatherImages[0].icon)

                val probabilityPercent = "N/A"

                forecastList.add(
                    Weather(
                        it.date,
                        probabilityPercent,
                        it.temperature.min ?: 0.0,
                        it.temperature.max ?: 0.0,
                        dayThemeImageURL,
                        nightThemeImageURL,
                        it.weatherImages[0].description
                    )
                )
            }
        }


        if (model.hourlyWeather?.isNotEmpty() == true) {
            model.hourlyWeather.forEach {

                val dayThemeImageURL =
                    String.format(OpenWeatherMapApiService.BASE_IMAGE_URL, it.weatherImages[0].icon)

                val nightThemeImageURL =
                    String.format(OpenWeatherMapApiService.BASE_IMAGE_URL, it.weatherImages[0].icon)

                val probabilityPercent = "N/A"

                forecastList.add(
                    Weather(
                        it.date,
                        probabilityPercent,
                        it.temperature.min ?: 0.0,
                        it.temperature.max ?: 0.0,
                        dayThemeImageURL,
                        nightThemeImageURL,
                        it.weatherImages[0].description
                    )
                )
            }
        }

        return WeatherForecast(
            headlines = model.currentWeather?.weatherImages?.get(0)?.description ?: "",
            forecasts = forecastList
        )
    }

    override fun mapFromDomainModel(domainModel: WeatherForecast): OpenWeatherMapApiResponse {
        TODO("Not yet implemented")
    }
}
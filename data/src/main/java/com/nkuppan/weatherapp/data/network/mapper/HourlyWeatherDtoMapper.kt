package com.nkuppan.weatherapp.data.network.mapper

import com.nkuppan.weatherapp.data.network.response.OpenWeatherMapApiResponse
import com.nkuppan.weatherapp.domain.mapper.DomainMapper
import com.nkuppan.weatherapp.domain.model.Weather

class HourlyWeatherDtoMapper : DomainMapper<OpenWeatherMapApiResponse, List<Weather>> {
    override fun dtoToDomainModel(dtoObject: OpenWeatherMapApiResponse): List<Weather> {
        return dtoObject.hourlyWeather?.map {
            Weather(
                it.date,
                "${it.pop * 100} %",
                it.temperature,
                it.temperature,
                it.weatherImages[0].getDayThemeImageURL(),
                it.weatherImages[0].getNightThemeImageURL(),
                it.weatherImages[0].description,
                it.weatherImages[0].main,
                feelsLikeTemperature = it.feelsLikeTemperature
            )
        } ?: emptyList()
    }
}
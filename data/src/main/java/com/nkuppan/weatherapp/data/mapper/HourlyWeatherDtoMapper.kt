package com.nkuppan.weatherapp.data.mapper

import com.nkuppan.weatherapp.data.network.response.OpenWeatherMapApiResponse
import com.nkuppan.weatherapp.domain.mapper.Mapper
import com.nkuppan.weatherapp.domain.model.Weather
import javax.inject.Inject

class HourlyWeatherDtoMapper @Inject constructor() :
    Mapper<OpenWeatherMapApiResponse, List<Weather>> {
    override fun convert(fromObject: OpenWeatherMapApiResponse): List<Weather> {
        return fromObject.hourlyWeather?.map {
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
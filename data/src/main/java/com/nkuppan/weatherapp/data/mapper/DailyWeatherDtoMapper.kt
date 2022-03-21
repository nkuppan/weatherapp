package com.nkuppan.weatherapp.data.mapper

import com.nkuppan.weatherapp.data.network.response.OpenWeatherMapApiResponse
import com.nkuppan.weatherapp.domain.mapper.Mapper
import com.nkuppan.weatherapp.domain.model.Weather
import javax.inject.Inject

class DailyWeatherDtoMapper @Inject constructor() :
    Mapper<OpenWeatherMapApiResponse, List<Weather>> {
    override fun convert(fromObject: OpenWeatherMapApiResponse): List<Weather> {
        return fromObject.dailyWeather?.map {
            Weather(
                it.date,
                "${it.pop * 100} %",
                it.temperature.min ?: 0.0,
                it.temperature.max ?: 0.0,
                it.weatherImages[0].getDayThemeImageURL(),
                it.weatherImages[0].getNightThemeImageURL(),
                it.weatherImages[0].description,
                it.weatherImages[0].main,
                feelsLikeTemperature = it.feelsLikeTemperature.max ?: 0.0
            )
        } ?: emptyList()
    }
}
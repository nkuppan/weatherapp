package com.nkuppan.weatherapp.data.network.model

import com.nkuppan.weatherapp.data.network.response.WeatherForecastApiResponse
import com.nkuppan.weatherapp.domain.mapper.DomainMapper
import com.nkuppan.weatherapp.domain.model.Weather
import com.nkuppan.weatherapp.domain.model.WeatherForecast

class WeatherDtoMapper : DomainMapper<WeatherForecastApiResponse, WeatherForecast> {

    override fun mapToDomainModel(model: WeatherForecastApiResponse): WeatherForecast {
        val forecastList = mutableListOf<Weather>()
        if (model.forecastList.isNotEmpty()) {
            model.forecastList.forEach {
                forecastList.add(
                    Weather(
                        it.date,
                        it.degree,
                        it.degree,
                        it.weatherImages[0].icon,
                        it.weatherImages[0].description
                    )
                )
            }
        }
        return WeatherForecast(
            cityName = model.city.name,
            count = model.count,
            forecastList
        )
    }

    override fun mapFromDomainModel(domainModel: WeatherForecast): WeatherForecastApiResponse {
        TODO("Not yet implemented")
    }
}
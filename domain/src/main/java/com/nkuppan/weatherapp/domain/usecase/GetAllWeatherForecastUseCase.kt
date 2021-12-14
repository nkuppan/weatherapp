package com.nkuppan.weatherapp.domain.usecase

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.WeatherType
import com.nkuppan.weatherapp.domain.model.WeatherUIModel
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class GetAllWeatherForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        city: City,
        numberOfHours: Int,
        numberOfDays: Int
    ): Resource<List<WeatherUIModel>> {

        if (!city.isValidCity()) {
            return Resource.Error(KotlinNullPointerException("Invalid city or information"))
        }

        return when (val response =
            repository.getAllWeatherForecast(city, numberOfHours, numberOfDays)) {
            is Resource.Success -> {
                val output = mutableListOf<WeatherUIModel>()

                response.data.forEach { value ->
                    if (value.key == WeatherType.HOURLY) {
                        output.add(WeatherUIModel(value.key, value.value))
                    } else {
                        output.addAll(
                            value.value.map {
                                WeatherUIModel(value.key, listOf(it))
                            }
                        )
                    }
                }

                output.sortBy {
                    it.type.ordinal
                }

                Resource.Success(output)
            }
            is Resource.Error -> {
                response
            }
        }
    }
}
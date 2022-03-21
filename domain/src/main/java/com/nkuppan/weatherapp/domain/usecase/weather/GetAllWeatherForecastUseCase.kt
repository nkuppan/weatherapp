package com.nkuppan.weatherapp.domain.usecase.weather

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.WeatherType
import com.nkuppan.weatherapp.domain.model.WeatherTypeModel
import com.nkuppan.weatherapp.domain.respository.WeatherRepository

class GetAllWeatherForecastUseCase(private val repository: WeatherRepository) {

    suspend operator fun invoke(
        city: City,
        numberOfHours: Int,
        numberOfDays: Int
    ): Resource<List<WeatherTypeModel>> {

        if (!city.isValidCity()) {
            return Resource.Error(KotlinNullPointerException("Invalid city or information"))
        }

        return when (val response =
            repository.getAllWeatherForecast(city, numberOfHours, numberOfDays)) {
            is Resource.Success -> {
                val output = mutableListOf<WeatherTypeModel>()

                response.data.forEach { value ->
                    if (value.key == WeatherType.HOURLY) {
                        output.add(WeatherTypeModel(value.key, value.value))
                    } else {
                        output.addAll(
                            value.value.map {
                                WeatherTypeModel(value.key, listOf(it))
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
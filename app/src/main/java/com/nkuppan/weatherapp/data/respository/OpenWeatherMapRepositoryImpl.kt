package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.BuildConfig
import com.nkuppan.weatherapp.core.extention.NetworkResult
import com.nkuppan.weatherapp.data.network.OpenWeatherMapApiService
import com.nkuppan.weatherapp.data.network.model.OpenWeatherDtoMapper
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class OpenWeatherMapRepositoryImpl(
    private val service: OpenWeatherMapApiService,
    private val weatherDtoMapper: OpenWeatherDtoMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getAccWeatherCityId(cityName: String): NetworkResult<List<City>> =
        NetworkResult.Success(listOf(City(cityName, cityName, 1, cityName)))


    override suspend fun getHourlyWeatherForecast(
        cityId: String,
        numberOfHours: Int
    ): NetworkResult<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getCurrentAndForecastData(
                appId = BuildConfig.WEATHER_API_KEY,
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(
                    weatherDtoMapper.mapToDomainModel(response.body()!!)
                )
            } else {
                NetworkResult.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    override suspend fun getDailyWeatherForecast(
        cityId: String,
        numberOfDays: Int
    ): NetworkResult<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getCurrentAndForecastData(
                appId = BuildConfig.WEATHER_API_KEY
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(weatherDtoMapper.mapToDomainModel(response.body()!!))
            } else {
                NetworkResult.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}

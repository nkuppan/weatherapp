package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.BuildConfig
import com.nkuppan.weatherapp.core.extention.Result
import com.nkuppan.weatherapp.data.network.WeatherApiService
import com.nkuppan.weatherapp.data.network.model.WeatherDtoMapper
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class WeatherRepositoryImpl(
    private val service: WeatherApiService,
    private val mapper: WeatherDtoMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getHourlyWeatherForecast(
        cityName: String,
        numberOfHours: Int
    ): Result<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getHourlyForecastUsingCityName(
                cityName = cityName,
                appId = BuildConfig.WEATHER_API_KEY
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(mapper.mapToDomainModel(response.body()!!))
            } else {
                Result.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getDailyWeatherForecast(
        cityName: String,
        numberOfDays: Int
    ): Result<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getDailyForecastUsingCityName(
                cityName = cityName,
                appId = BuildConfig.WEATHER_API_KEY
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(mapper.mapToDomainModel(response.body()!!))
            } else {
                Result.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

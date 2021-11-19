package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.BuildConfig
import com.nkuppan.weatherapp.core.extention.NetworkResult
import com.nkuppan.weatherapp.data.network.OpenWeatherMapApiService
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class OpenWeatherMapRepositoryImpl(
    private val service: OpenWeatherMapApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getCityInfo(cityName: String): NetworkResult<List<City>> =
        withContext(dispatcher)
        {
            return@withContext try {
                val response = service.getCities(
                    cityName = cityName,
                    appId = BuildConfig.OPEN_WEATHER_API_KEY
                ).awaitResponse()
                if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                    NetworkResult.Success(response.body()!!.map { it.toCity() })
                } else {
                    NetworkResult.Error(KotlinNullPointerException())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e)
            }
        }


    override suspend fun getHourlyWeatherForecast(
        city: City,
        numberOfHours: Int
    ): NetworkResult<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getCurrentAndForecastData(
                appId = BuildConfig.OPEN_WEATHER_API_KEY,
                latitude = city.latitude,
                longitude = city.longitude
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!.toHourlyWeatherForecast())
            } else {
                NetworkResult.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    override suspend fun getDailyWeatherForecast(
        city: City,
        numberOfDays: Int
    ): NetworkResult<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getCurrentAndForecastData(
                appId = BuildConfig.OPEN_WEATHER_API_KEY,
                latitude = city.latitude,
                longitude = city.longitude
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!.toDailyWeatherForecast())
            } else {
                NetworkResult.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}

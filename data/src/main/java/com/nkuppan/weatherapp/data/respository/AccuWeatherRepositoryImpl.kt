package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.core.BuildConfig
import com.nkuppan.weatherapp.data.network.AccWeatherApiService
import com.nkuppan.weatherapp.domain.model.*
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class AccuWeatherRepositoryImpl(
    private val service: AccWeatherApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getCityInfo(cityName: String): Resource<List<City>> =
        withContext(dispatcher) {
            return@withContext try {
                val response = service.getCities(
                    cityName = cityName,
                    apiKey = BuildConfig.ACC_WEATHER_API_KEY
                ).awaitResponse()
                if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                    Resource.Success(response.body()!!.map { it.toCity() })
                } else {
                    Resource.Error(KotlinNullPointerException())
                }
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }

    override suspend fun getHourlyWeatherForecast(
        city: City,
        numberOfHours: Int
    ): Resource<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getHourlyForecastUsingCityId(
                cityId = city.key,
                appKey = BuildConfig.ACC_WEATHER_API_KEY,
                numberOfHours = "${numberOfHours}hour"
            ).awaitResponse()
            if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                Resource.Success(
                    WeatherForecast(
                        headlines = "",
                        forecasts = response.body()!!.map { it.toWeather() }
                    )
                )
            } else {
                Resource.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getDailyWeatherForecast(
        city: City,
        numberOfDays: Int
    ): Resource<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getDailyForecastUsingCityId(
                cityId = city.key,
                appKey = BuildConfig.ACC_WEATHER_API_KEY,
                numberOfDays = "${numberOfDays}day"
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.toWeatherForecast())
            } else {
                Resource.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getAllWeatherForecast(
        city: City,
        numberOfHours: Int,
        numberOfDays: Int
    ): Resource<Map<WeatherType, List<Weather>>> {
        TODO("Not yet implemented")
    }
}

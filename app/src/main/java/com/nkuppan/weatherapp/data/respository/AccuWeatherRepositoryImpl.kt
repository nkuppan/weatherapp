package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.BuildConfig
import com.nkuppan.weatherapp.core.extention.NetworkResult
import com.nkuppan.weatherapp.data.network.AccWeatherApiService
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class AccuWeatherRepositoryImpl(
    private val service: AccWeatherApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getCityInfo(cityName: String): NetworkResult<List<City>> =
        withContext(dispatcher) {
            return@withContext try {
                val response = service.getCities(
                    cityName = cityName,
                    apiKey = BuildConfig.ACC_WEATHER_API_KEY
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
            val response = service.getHourlyForecastUsingCityId(
                cityId = city.key,
                appKey = BuildConfig.ACC_WEATHER_API_KEY,
                numberOfHours = "${numberOfHours}hour"
            ).awaitResponse()
            if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                NetworkResult.Success(
                    WeatherForecast(
                        headlines = "",
                        forecasts = response.body()!!.map { it.toWeather() }
                    )
                )
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
            val response = service.getDailyForecastUsingCityId(
                cityId = city.key,
                appKey = BuildConfig.ACC_WEATHER_API_KEY,
                numberOfDays = "${numberOfDays}day"
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!.toWeatherForecast())
            } else {
                NetworkResult.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}

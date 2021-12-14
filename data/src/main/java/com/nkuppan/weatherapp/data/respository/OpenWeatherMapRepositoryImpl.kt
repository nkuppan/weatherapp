package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.core.BuildConfig
import com.nkuppan.weatherapp.data.network.OpenWeatherMapApiService
import com.nkuppan.weatherapp.data.network.mapper.CurrentWeatherDtoMapper
import com.nkuppan.weatherapp.data.network.mapper.DailyWeatherDtoMapper
import com.nkuppan.weatherapp.data.network.mapper.HourlyWeatherDtoMapper
import com.nkuppan.weatherapp.domain.model.*
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class OpenWeatherMapRepositoryImpl(
    private val service: OpenWeatherMapApiService,
    private val currentWeatherDtoMapper: CurrentWeatherDtoMapper,
    private val hourlyWeatherDtoMapper: HourlyWeatherDtoMapper,
    private val dailyWeatherDtoMapper: DailyWeatherDtoMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getCityInfo(cityName: String): Resource<List<City>> =
        withContext(dispatcher)
        {
            return@withContext try {
                val response = service.getCities(
                    cityName = cityName,
                    appId = BuildConfig.OPEN_WEATHER_API_KEY
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
            val response = service.getCurrentAndForecastData(
                appId = BuildConfig.OPEN_WEATHER_API_KEY,
                latitude = city.latitude!!,
                longitude = city.longitude!!
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(
                    WeatherForecast(
                        headlines = "",
                        forecasts = hourlyWeatherDtoMapper.dtoToDomainModel(response.body()!!)
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
            val response = service.getCurrentAndForecastData(
                appId = BuildConfig.OPEN_WEATHER_API_KEY,
                latitude = city.latitude!!,
                longitude = city.longitude!!
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(
                    WeatherForecast(
                        headlines = "",
                        forecasts = dailyWeatherDtoMapper.dtoToDomainModel(response.body()!!)
                    )
                )
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
    ): Resource<Map<WeatherType, List<Weather>>> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getCurrentAndForecastData(
                appId = BuildConfig.OPEN_WEATHER_API_KEY,
                latitude = city.latitude!!,
                longitude = city.longitude!!
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Resource.Success(
                    hashMapOf(
                        WeatherType.CURRENT to currentWeatherDtoMapper.dtoToDomainModel(body),
                        WeatherType.HOURLY to hourlyWeatherDtoMapper.dtoToDomainModel(body),
                        WeatherType.DAILY to dailyWeatherDtoMapper.dtoToDomainModel(body)
                    )
                )
            } else {
                Resource.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}

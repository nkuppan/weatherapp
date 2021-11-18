package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.BuildConfig
import com.nkuppan.weatherapp.core.extention.NetworkResult
import com.nkuppan.weatherapp.data.network.AccWeatherApiService
import com.nkuppan.weatherapp.data.network.model.CityDtoMapper
import com.nkuppan.weatherapp.data.network.model.DailyWeatherDtoMapper
import com.nkuppan.weatherapp.data.network.model.HourlyWeatherDtoMapper
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.WeatherForecast
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class AccuWeatherRepositoryImpl(
    private val service: AccWeatherApiService,
    private val dailyWeatherDtoMapper: DailyWeatherDtoMapper,
    private val hourlyWeatherDtoMapper: HourlyWeatherDtoMapper,
    private val cityDtoMapper: CityDtoMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getAccWeatherCityId(cityName: String): NetworkResult<List<City>> =
        withContext(dispatcher) {
            return@withContext try {
                val response = service.getCities(
                    cityName = cityName,
                    apiKey = BuildConfig.WEATHER_API_KEY
                ).awaitResponse()
                if (response.isSuccessful && response.body() != null) {
                    NetworkResult.Success(cityDtoMapper.toDomainList(response.body()!!))
                } else {
                    NetworkResult.Error(KotlinNullPointerException())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e)
            }
        }

    override suspend fun getHourlyWeatherForecast(
        cityId: String,
        numberOfHours: Int
    ): NetworkResult<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getHourlyForecastUsingCityId(
                cityId = cityId,
                appKey = BuildConfig.WEATHER_API_KEY,
                numberOfHours = "${numberOfHours}hour"
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(
                    WeatherForecast(
                        headlines = "",
                        forecasts = hourlyWeatherDtoMapper.toDomainList(response.body()!!)
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
        cityId: String,
        numberOfDays: Int
    ): NetworkResult<WeatherForecast> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getDailyForecastUsingCityId(
                cityId = cityId,
                appKey = BuildConfig.WEATHER_API_KEY,
                numberOfDays = "${numberOfDays}day"
            ).awaitResponse()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(dailyWeatherDtoMapper.mapToDomainModel(response.body()!!))
            } else {
                NetworkResult.Error(KotlinNullPointerException())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}

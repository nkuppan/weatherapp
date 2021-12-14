package com.nkuppan.weatherapp.data.network

import com.nkuppan.weatherapp.data.network.response.CityDto
import com.nkuppan.weatherapp.data.network.response.WeatherForecastDailyApiResponse
import com.nkuppan.weatherapp.data.network.response.WeatherForecastHourlyApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccWeatherApiService {

    @GET("locations/v1/cities/search")
    fun getCities(
        @Query("q") cityName: String,
        @Query("apikey") apiKey: String
    ): Call<List<CityDto>>

    @GET("forecasts/v1/daily/{days}/{cityId}")
    fun getDailyForecastUsingCityId(
        @Path("cityId") cityId: String,
        @Path("days") numberOfDays: String = FORECAST_DAYS,
        @Query("apikey") appKey: String,
        @Query("metric") metric: Boolean = DEFAULT_METRIC,
        @Query("language") lang: String = DEFAULT_LANG
    ): Call<WeatherForecastDailyApiResponse>

    @GET("forecasts/v1/hourly/{hours}/{cityId}")
    fun getHourlyForecastUsingCityId(
        @Path("cityId") cityId: String,
        @Path("hours") numberOfHours: String = FORECAST_HOURS,
        @Query("apikey") appKey: String,
        @Query("metric") metric: Boolean = DEFAULT_METRIC,
        @Query("language") lang: String = DEFAULT_LANG
    ): Call<List<WeatherForecastHourlyApiResponse>>


    companion object {
        const val DEFAULT_METRIC = true // Will produce values in metric system

        const val FORECAST_DAYS = "5day"
        const val FORECAST_HOURS = "12hour"

        const val DEFAULT_LANG = "en"

        const val BASE_URL = "http://dataservice.accuweather.com/"
        const val BASE_IMAGE_URL = "https://developer.accuweather.com/sites/default/files/%s-s.png"
    }
}
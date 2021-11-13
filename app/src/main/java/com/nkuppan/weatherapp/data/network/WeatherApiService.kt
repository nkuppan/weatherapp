package com.nkuppan.weatherapp.data.network

import com.nkuppan.weatherapp.data.network.response.WeatherForecastApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast/daily")
    fun getDailyForecastUsingCityName(
        @Query("q") cityName: String,
        @Query("appid") appId: String,
        @Query("cnt") numberOfDays: Int = FORECAST_DAYS,
        @Query("mode") mode: String = JSON_MODE,
        @Query("units") units: String = STANDARD_UNIT,
        @Query("lang") lang: String = DEFAULT_LANG
    ): Call<WeatherForecastApiResponse>

    @GET("forecast/hourly")
    fun getHourlyForecastUsingCityName(
        @Query("q") cityName: String,
        @Query("appid") appId: String,
        @Query("cnt") numberOfDays: Int = FORECAST_DAYS,
        @Query("mode") mode: String = JSON_MODE,
        @Query("units") units: String = STANDARD_UNIT,
        @Query("lang") lang: String = DEFAULT_LANG
    ): Call<WeatherForecastApiResponse>


    companion object {
        const val FORECAST_DAYS = 7

        const val STANDARD_UNIT = "standard"
        const val METRIC_UNIT = "metric"
        const val IMPERIAL_UNIT = "imperial"

        const val DEFAULT_LANG = "en"

        const val JSON_MODE = "json"
        const val XML_MODE = "xml"
        const val HTML_13 = "html"

        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val BASE_IMAGE_URL = "https://https://openweathermap.org/img/wn/%s.png"
    }
}
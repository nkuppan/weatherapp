package com.nkuppan.weatherapp.data.network

import com.nkuppan.weatherapp.data.network.response.OpenWeatherCityDto
import com.nkuppan.weatherapp.data.network.response.OpenWeatherMapApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApiService {

    @GET("geo/1.0/direct")
    fun getCities(
        @Query("q") cityName: String,
        @Query("appid") appId: String,
        @Query("limit") limit: Int = CITY_SEARCH_LIMIT_VALUE
    ): Call<List<OpenWeatherCityDto>>

    @GET("data/2.5/onecall")
    fun getCurrentAndForecastData(
        @Query("appid") appId: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: List<String> = DEFAULT_EXCLUDE,
        @Query("units") metric: String = DEFAULT_METRIC,
        @Query("lang") lang: String = DEFAULT_LANG
    ): Call<OpenWeatherMapApiResponse>


    companion object {
        const val CITY_SEARCH_LIMIT_VALUE = 5

        const val DEFAULT_METRIC = "metric" // Will produce values in metric system

        const val DEFAULT_LANG = "en"

        val DEFAULT_EXCLUDE = listOf("minutely")

        const val BASE_URL = "https://api.openweathermap.org/"
        const val BASE_IMAGE_URL = "https://openweathermap.org/img/wn/%s@2x.png"
    }
}
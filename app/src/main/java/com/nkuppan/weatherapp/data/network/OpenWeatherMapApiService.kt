package com.nkuppan.weatherapp.data.network

import com.nkuppan.weatherapp.data.network.response.OpenWeatherMapApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApiService {

    @GET("data/2.5/onecall")
    fun getCurrentAndForecastData(
        @Query("appid") appId: String,
        @Query("exclude") exclude: List<String> = DEFAULT_EXCLUDE,
        @Query("units") metric: String = DEFAULT_METRIC,
        @Query("lang") lang: String = DEFAULT_LANG
    ): Call<OpenWeatherMapApiResponse>


    companion object {
        const val DEFAULT_METRIC = "metric" // Will produce values in metric system

        const val DEFAULT_LANG = "en"

        val DEFAULT_EXCLUDE = listOf("hourly")

        const val BASE_URL = "https://api.openweathermap.org/"
        const val BASE_IMAGE_URL = "https://developer.accuweather.com/sites/default/files/0%d-s.png"
    }
}
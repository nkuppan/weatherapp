package com.nkuppan.weatherapp.data.network.response

import com.google.gson.annotations.SerializedName

data class OpenWeatherImageDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)


data class OpenWeatherTemperatureDto(
    @SerializedName("day")
    val day: Double?,
    @SerializedName("min")
    val min: Double?,
    @SerializedName("max")
    val max: Double?,
    @SerializedName("night")
    val night: Double?,
    @SerializedName("eve")
    val eve: Double?,
    @SerializedName("morn")
    val morn: Double?
)

data class HourlyOpenWeatherDto(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("moonrise")
    val moonrise: Long,
    @SerializedName("moonset")
    val moonset: Long,
    @SerializedName("temp")
    val temperature: OpenWeatherTemperatureDto,
    @SerializedName("feels_like")
    val feelsLikeTemperature: OpenWeatherTemperatureDto,
    @SerializedName("weather")
    val weatherImages: List<OpenWeatherImageDto>,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDegree: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int
)

data class DailyOpenWeatherDto(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("moonrise")
    val moonrise: Long,
    @SerializedName("moonset")
    val moonset: Long,
    @SerializedName("temp")
    val temperature: OpenWeatherTemperatureDto,
    @SerializedName("feels_like")
    val feelsLikeTemperature: OpenWeatherTemperatureDto,
    @SerializedName("weather")
    val weatherImages: List<OpenWeatherImageDto>,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDegree: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int
)

data class OpenWeatherMapApiResponse(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("current")
    val currentWeather: HourlyOpenWeatherDto? = null,
    @SerializedName("hourly")
    val hourlyWeather: List<HourlyOpenWeatherDto>? = null,
    @SerializedName("daily")
    val dailyWeather: List<DailyOpenWeatherDto>? = null
)
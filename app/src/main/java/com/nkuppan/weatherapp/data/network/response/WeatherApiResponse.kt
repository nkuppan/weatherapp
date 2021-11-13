package com.nkuppan.weatherapp.data.network.response

import com.google.gson.annotations.SerializedName

data class WeatherForecastApiResponse(
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("cnt")
    val count: Int,
    @SerializedName("city")
    val city: CityDto,
    @SerializedName("list")
    val forecastList: List<WeatherDto>
)

data class CityDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String
)

data class WeatherDto(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: TemperatureDto,
    @SerializedName("feels_like")
    val feelsLike: TemperatureDto,
    @SerializedName("weather")
    val weatherImages: List<WeatherImageDto>,
    @SerializedName("deg")
    val degree: Double
)

data class WeatherImageDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class TemperatureDto(
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
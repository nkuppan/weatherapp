package com.nkuppan.weatherapp.data.network.response

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.nkuppan.weatherapp.data.network.OpenWeatherMapApiService
import com.nkuppan.weatherapp.domain.model.City

data class OpenWeatherAlertDto(
    @SerializedName("sender_name")
    val senderName: String?,
    @SerializedName("event")
    val event: String?,
    @SerializedName("start")
    val start: Long?,
    @SerializedName("end")
    val end: Long?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("tags")
    val tags: List<String>?,
)

data class OpenWeatherCityDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("country")
    val country: String
) {
    fun toCity(): City {
        return City(
            name,
            "",
            1,
            country,
            latitude,
            longitude
        )
    }
}

data class OpenWeatherImageDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
) {
    fun getDayThemeImageURL(): String {
        return String.format(OpenWeatherMapApiService.BASE_IMAGE_URL, icon)
    }

    fun getNightThemeImageURL(): String {
        return String.format(OpenWeatherMapApiService.BASE_IMAGE_URL, icon)
    }
}


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
    val temperature: Double,
    @SerializedName("feels_like")
    val feelsLikeTemperature: Double,
    @SerializedName("weather")
    val weatherImages: List<OpenWeatherImageDto>,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDegree: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("uvi")
    val uvIndex: Double,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("rain")
    val rain: JsonObject,
    @SerializedName("pop")
    val pop: Double
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
    val pressure: Int,
    @SerializedName("pop")
    val pop: Double
)

data class OpenWeatherMapApiResponse(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("current")
    val currentWeather: HourlyOpenWeatherDto,
    @SerializedName("hourly")
    val hourlyWeather: List<HourlyOpenWeatherDto>? = null,
    @SerializedName("daily")
    val dailyWeather: List<DailyOpenWeatherDto>? = null,
    @SerializedName("alerts")
    val alerts: List<OpenWeatherAlertDto>? = null
)
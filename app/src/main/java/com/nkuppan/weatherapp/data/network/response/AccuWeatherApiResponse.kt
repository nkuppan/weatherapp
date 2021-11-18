package com.nkuppan.weatherapp.data.network.response

import com.google.gson.annotations.SerializedName

data class CityDto(
    @SerializedName("Key")
    val key: String,
    @SerializedName("EnglishName")
    val name: String,
    @SerializedName("Rank")
    val rank: Int,
    @SerializedName("Country")
    val country: CountryDto
)

data class CountryDto(
    @SerializedName("ID")
    val id: String,
    @SerializedName("EnglishName")
    val name: String
)

data class HeadLineDto(
    @SerializedName("EffectiveDate")
    val effectiveDate: String,
    @SerializedName("Severity")
    val severity: Int,
    @SerializedName("Text")
    val status: String,
    @SerializedName("Category")
    val category: String,
    @SerializedName("Link")
    val link: String,
    @SerializedName("MobileLink")
    val mobileLink: String
)

data class TemperatureValueDto(
    @SerializedName("Value")
    val value: Double,
    @SerializedName("UnitType")
    val unitType: Int,
    @SerializedName("Unit")
    val unit: String
)

data class TemperatureDto(
    @SerializedName("Minimum")
    val minimum: TemperatureValueDto,
    @SerializedName("Maximum")
    val maximum: TemperatureValueDto
)

data class WeatherImageDto(
    @SerializedName("Icon")
    val icon: Long,
    @SerializedName("IconPhrase")
    val description: String,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationType")
    val precipitationType: String?,
    @SerializedName("PrecipitationIntensity")
    val precipitationIntensity: String?,
    @SerializedName("PrecipitationProbability")
    val precipitationProbability: Double
)

data class WeatherDto(
    @SerializedName("Date")
    val date: String,
    @SerializedName("EpochDate")
    val dateTimeInMilliseconds: Long,
    @SerializedName("Temperature")
    val temperature: TemperatureDto,
    @SerializedName("Day")
    val dayThemeIcon: WeatherImageDto,
    @SerializedName("Night")
    val nightThemeIcon: WeatherImageDto,
    @SerializedName("Link")
    val link: String,
    @SerializedName("MobileLink")
    val mobileLink: String
)

data class WeatherForecastDailyApiResponse(
    @SerializedName("Headline")
    val headline: HeadLineDto,
    @SerializedName("DailyForecasts")
    val forecastList: List<WeatherDto>
)

data class WeatherForecastHourlyApiResponse(
    @SerializedName("DateTime")
    val dateTime: String,
    @SerializedName("EpochDateTime")
    val epocTime: Long,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int,
    @SerializedName("IconPhrase")
    val iconStatus: String,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationType")
    val precipitationType: String,
    @SerializedName("PrecipitationIntensity")
    val precipitationIntensity: String,
    @SerializedName("PrecipitationProbability")
    val precipitationProbability: Double,
    @SerializedName("IsDaylight")
    val isDaylight: Boolean,
    @SerializedName("Temperature")
    val temperature: TemperatureValueDto,
    @SerializedName("Link")
    val link: String,
    @SerializedName("MobileLink")
    val mobileLink: String
)
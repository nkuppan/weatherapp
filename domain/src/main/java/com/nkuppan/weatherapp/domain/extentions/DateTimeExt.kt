package com.nkuppan.weatherapp.domain.extentions

import java.text.SimpleDateFormat
import java.util.*

const val DAILY_FORECAST_DATE_FORMAT = "E, MMM, d"
const val HOURLY_FORECAST_24_HOUR_DATE_FORMAT = "HH:mm"
const val HOURLY_FORECAST_12_HOUR_DATE_FORMAT = "hh:mm a"

/**
 * Since the server is sending an unix time format to convert that into an
 */
fun Long.get12HourFormattedTime(): String {
    return SimpleDateFormat(
        HOURLY_FORECAST_12_HOUR_DATE_FORMAT,
        Locale.getDefault()
    ).format(Date(this * 1000L))
}

/**
 * Since the server is sending an unix time format to convert that into an
 */
fun Long.get24HourFormattedTime(): String {
    return SimpleDateFormat(
        HOURLY_FORECAST_24_HOUR_DATE_FORMAT,
        Locale.getDefault()
    ).format(Date(this * 1000L))
}

fun Long.getFormattedDate(): String {
    return SimpleDateFormat(
        DAILY_FORECAST_DATE_FORMAT,
        Locale.getDefault()
    ).format(Date(this * 1000L))
}
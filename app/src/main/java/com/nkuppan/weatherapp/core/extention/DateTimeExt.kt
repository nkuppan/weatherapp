package com.nkuppan.weatherapp.core.extention

import java.text.SimpleDateFormat
import java.util.*

const val DAILY_FORECAST_DATE_FORMAT = "E, MMM, d"
const val HOURLY_FORECAST_DATE_FORMAT = "hh:mm a"

/**
 * Since the server is sending an unix time format to convert that into an
 */
fun Long.getFormattedTime(): String {
    return SimpleDateFormat(
        HOURLY_FORECAST_DATE_FORMAT,
        Locale.getDefault()
    ).format(Date(this * 1000L))
}

fun Long.getFormattedDate(): String {
    return SimpleDateFormat(
        DAILY_FORECAST_DATE_FORMAT,
        Locale.getDefault()
    ).format(Date(this * 1000L))
}
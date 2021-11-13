package com.nkuppan.weatherapp.extension


fun Long.getFormattedTime(): String {
    return "00.00 AM"
}

fun Long.getFormattedDate(): String {
    return "Fri, Nov 1"
}
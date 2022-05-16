package com.nkuppan.weatherapp.utils


fun Double.toMillimeterToInches(): Double {
    return this / 25.4
}

fun Double.toFahrenheit(): Double {
    return this * 1.8 + 32
}

fun Double.toKilometerPerHour(): Double {
    return this * 1.609
}

fun Double.toMeterPerSecond(): Double {
    return this * 2.237
}

fun Double.toInchesOfMercury(): Double {
    return this / 33.863886666667
}

fun Double.toKilometer(): Double {
    return this / 1000
}

fun Double.toMiles(): Double {
    return this / 1609.34
}

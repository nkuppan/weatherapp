package com.nkuppan.weatherapp.data.extension

import com.google.gson.Gson

fun Any.objectToJsonString(): String {
    return Gson().toJson(this)
}

inline fun <reified T> String.jsonStringToObject(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (exception: Exception) {
        null
    }
}
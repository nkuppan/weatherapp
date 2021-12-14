package com.nkuppan.weatherapp.domain.extentions

import com.nkuppan.weatherapp.domain.utils.MAX_QUERY_LENGTH

fun String?.isValidQueryString(): Boolean {

    if (isNullOrBlank()) {
        return false
    }

    if (trim().length < MAX_QUERY_LENGTH) {
        return false
    }

    return true
}

package com.nkuppan.weatherapp.core.utils

import kotlinx.coroutines.CoroutineDispatcher

class AppCoroutineDispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher
)
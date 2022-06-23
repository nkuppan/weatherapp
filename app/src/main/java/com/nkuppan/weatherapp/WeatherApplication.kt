package com.nkuppan.weatherapp

import android.app.Application
import com.nkuppan.weatherapp.domain.usecase.settings.ApplyThemeUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@HiltAndroidApp
class WeatherApplication : Application() {

    @Inject
    lateinit var applyThemeUseCase: ApplyThemeUseCase

    override fun onCreate() {
        super.onCreate()
        setupTheme()
    }

    private fun setupTheme() {
        GlobalScope.launch {
            applyThemeUseCase.invoke()
        }
    }
}
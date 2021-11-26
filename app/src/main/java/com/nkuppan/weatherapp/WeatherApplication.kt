package com.nkuppan.weatherapp

import android.app.Application
import com.nkuppan.weatherapp.domain.respository.ThemeRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication : Application() {

    @Inject
    lateinit var themeRepository: ThemeRepository

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        setupTheme()
    }

    @DelicateCoroutinesApi
    private fun setupTheme() {
        GlobalScope.launch {
            themeRepository.applyTheme(this, Dispatchers.Main)
        }
    }
}
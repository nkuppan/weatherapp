package com.nkuppan.weatherapp.data.repository

import androidx.appcompat.app.AppCompatDelegate
import com.nkuppan.weatherapp.domain.model.Theme
import com.nkuppan.weatherapp.domain.respository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeThemeRepository : ThemeRepository {

    private var selectedTheme = Theme.LIGHT_THEME

    override suspend fun saveTheme(theme: Theme): Boolean {
        this.selectedTheme = theme
        return true
    }

    override suspend fun applyTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun getSelectedTheme(): Flow<Theme> = flow {
        selectedTheme
    }
}
package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    /**
     * Storing the selected theme
     */
    suspend fun saveTheme(theme: Theme): Boolean

    /**
     * Storing the selected theme
     */
    suspend fun applyTheme()

    /**
     * Reading the selected theme
     */
    fun getSelectedTheme(): Flow<Theme>
}
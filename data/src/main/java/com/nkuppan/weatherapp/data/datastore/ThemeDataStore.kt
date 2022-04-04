package com.nkuppan.weatherapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.nkuppan.weatherapp.domain.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeDataStore(private val dataStore: DataStore<Preferences>) {

    suspend fun setTheme(theme: Theme) = dataStore.edit { preferences ->
        preferences[KEY_THEME_MODE] = theme.ordinal
    }

    fun getTheme(): Flow<Theme> = dataStore.data.map { preferences ->
        Theme.values()[preferences[KEY_THEME_MODE] ?: Theme.LIGHT_THEME.ordinal]
    }

    companion object {
        private val KEY_THEME_MODE = intPreferencesKey("mode")
    }
}

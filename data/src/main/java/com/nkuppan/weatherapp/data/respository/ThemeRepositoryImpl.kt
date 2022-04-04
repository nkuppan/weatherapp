package com.nkuppan.weatherapp.data.respository

import androidx.appcompat.app.AppCompatDelegate
import com.nkuppan.weatherapp.data.datastore.ThemeDataStore
import com.nkuppan.weatherapp.domain.model.Theme
import com.nkuppan.weatherapp.domain.respository.ThemeRepository
import com.nkuppan.weatherapp.domain.utils.AppCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ThemeRepositoryImpl(
    private val dataStore: ThemeDataStore,
    private val dispatchers: AppCoroutineDispatchers
) : ThemeRepository {

    private fun getMode(theme: Theme): Int {
        return when (theme) {
            Theme.LIGHT_THEME -> {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            Theme.DARK_THEME -> {
                AppCompatDelegate.MODE_NIGHT_YES
            }
        }
    }

    override suspend fun saveTheme(theme: Theme): Boolean = withContext(dispatchers.io) {
        val mode = getMode(theme)
        withContext(dispatchers.main) {
            AppCompatDelegate.setDefaultNightMode(mode)
        }
        dataStore.setTheme(theme)
        return@withContext true
    }

    override suspend fun applyTheme() {
        val theme = getSelectedTheme().first()
        withContext(dispatchers.main) {
            val mode = getMode(theme)
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    override fun getSelectedTheme(): Flow<Theme> {
        return dataStore.getTheme()
    }
}

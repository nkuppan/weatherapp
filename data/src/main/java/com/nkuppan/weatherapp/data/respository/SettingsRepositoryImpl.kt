package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.data.datastore.SettingsDataStore
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SettingsRepository {

    override suspend fun saveCountry(city: City): Unit = withContext(coroutineDispatcher) {
        dataStore.setCountry(city)
    }

    override fun getSelectedCountry(): Flow<City> {
        return dataStore.getCountry()
    }
}

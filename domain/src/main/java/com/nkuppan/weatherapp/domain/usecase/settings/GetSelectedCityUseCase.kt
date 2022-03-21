package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedCityUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<City> {
        return repository.getSelectedCountry()
    }
}
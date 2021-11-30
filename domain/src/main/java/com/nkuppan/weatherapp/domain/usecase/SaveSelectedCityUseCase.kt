package com.nkuppan.weatherapp.domain.usecase

import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.respository.SettingsRepository

class SaveSelectedCityUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(city: City) {
        repository.saveCountry(city)
    }
}
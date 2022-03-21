package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.WindSpeed
import com.nkuppan.weatherapp.domain.respository.SettingsRepository

class SaveWindSpeedUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(windSpeed: WindSpeed): Resource<Boolean> {
        return Resource.Success(repository.saveWindSpeed(windSpeed))
    }
}
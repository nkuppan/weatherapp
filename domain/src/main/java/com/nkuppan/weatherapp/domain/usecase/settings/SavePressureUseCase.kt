package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Pressure
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.respository.SettingsRepository

class SavePressureUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(pressure: Pressure): Resource<Boolean> {
        return Resource.Success(repository.savePressure(pressure))
    }
}
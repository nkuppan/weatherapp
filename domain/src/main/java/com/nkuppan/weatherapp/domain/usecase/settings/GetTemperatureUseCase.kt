package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Temperature
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetTemperatureUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<Temperature> {
        return repository.getTemperature()
    }
}
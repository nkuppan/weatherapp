package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.WindSpeed
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetWindSpeedUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<WindSpeed> {
        return repository.getWindSpeed()
    }
}
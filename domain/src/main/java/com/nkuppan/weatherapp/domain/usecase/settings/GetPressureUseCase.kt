package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Pressure
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetPressureUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<Pressure> {
        return repository.getPressure()
    }
}
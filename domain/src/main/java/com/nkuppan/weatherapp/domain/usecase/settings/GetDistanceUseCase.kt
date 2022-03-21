package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Distance
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetDistanceUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<Distance> {
        return repository.getDistance()
    }
}
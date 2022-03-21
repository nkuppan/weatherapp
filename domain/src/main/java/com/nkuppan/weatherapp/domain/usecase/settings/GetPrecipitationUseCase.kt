package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Precipitation
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetPrecipitationUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<Precipitation> {
        return repository.getPrecipitation()
    }
}
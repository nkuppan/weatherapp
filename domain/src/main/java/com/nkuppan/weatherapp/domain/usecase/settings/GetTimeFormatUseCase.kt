package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.TimeFormat
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetTimeFormatUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<TimeFormat> {
        return repository.getTimeFormat()
    }
}
package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.TimeFormat
import com.nkuppan.weatherapp.domain.respository.SettingsRepository

class SaveTimeFormatUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(timeFormat: TimeFormat): Resource<Boolean> {
        return Resource.Success(repository.saveTimeFormat(timeFormat))
    }
}
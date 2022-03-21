package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Distance
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.respository.SettingsRepository

class SaveDistanceUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(distance: Distance): Resource<Boolean> {
        return Resource.Success(repository.saveDistance(distance))
    }
}
package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.Theme
import com.nkuppan.weatherapp.domain.respository.ThemeRepository

class SaveThemeUseCase(private val repository: ThemeRepository) {

    suspend operator fun invoke(theme: Theme): Resource<Boolean> {
        return Resource.Success(repository.saveTheme(theme))
    }
}
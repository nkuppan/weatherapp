package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Theme
import com.nkuppan.weatherapp.domain.respository.ThemeRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(private val repository: ThemeRepository) {

    operator fun invoke(): Flow<Theme> {
        return repository.getSelectedTheme()
    }
}
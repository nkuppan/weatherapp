package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.respository.ThemeRepository

class ApplyThemeUseCase(private val repository: ThemeRepository) {

    suspend operator fun invoke() {
        repository.applyTheme()
    }
}
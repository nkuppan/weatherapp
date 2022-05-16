package com.nkuppan.weatherapp.di

import com.nkuppan.weatherapp.domain.respository.FavoriteCityRepository
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import com.nkuppan.weatherapp.domain.respository.ThemeRepository
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import com.nkuppan.weatherapp.domain.usecase.favorite.GetAllFavoriteCitiesUseCase
import com.nkuppan.weatherapp.domain.usecase.favorite.SaveFavoriteCityUseCase
import com.nkuppan.weatherapp.domain.usecase.settings.*
import com.nkuppan.weatherapp.domain.usecase.weather.GetAllWeatherForecastUseCase
import com.nkuppan.weatherapp.domain.usecase.weather.GetCityDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTemperatureUseCase(
        repository: SettingsRepository
    ): GetTemperatureUseCase {
        return GetTemperatureUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveTemperatureUseCase(
        repository: SettingsRepository
    ): SaveTemperatureUseCase {
        return SaveTemperatureUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWindSpeedUseCase(
        repository: SettingsRepository
    ): GetWindSpeedUseCase {
        return GetWindSpeedUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveWindSpeedUseCase(
        repository: SettingsRepository
    ): SaveWindSpeedUseCase {
        return SaveWindSpeedUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPressureUseCase(
        repository: SettingsRepository
    ): GetPressureUseCase {
        return GetPressureUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSavePressureUseCase(
        repository: SettingsRepository
    ): SavePressureUseCase {
        return SavePressureUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPrecipitationUseCase(
        repository: SettingsRepository
    ): GetPrecipitationUseCase {
        return GetPrecipitationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSavePrecipitationUseCase(
        repository: SettingsRepository
    ): SavePrecipitationUseCase {
        return SavePrecipitationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDistanceUseCase(
        repository: SettingsRepository
    ): GetDistanceUseCase {
        return GetDistanceUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveDistanceUseCase(
        repository: SettingsRepository
    ): SaveDistanceUseCase {
        return SaveDistanceUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTimeFormatUseCase(
        repository: SettingsRepository
    ): GetTimeFormatUseCase {
        return GetTimeFormatUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveTimeFormatUseCase(
        repository: SettingsRepository
    ): SaveTimeFormatUseCase {
        return SaveTimeFormatUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSelectedCityUseCase(
        settingsRepository: SettingsRepository
    ): GetSelectedCityUseCase {
        return GetSelectedCityUseCase(settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSaveSelectedCityUseCase(
        settingsRepository: SettingsRepository
    ): SaveSelectedCityUseCase {
        return SaveSelectedCityUseCase(settingsRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllFavoriteCitiesUseCase(
        repository: FavoriteCityRepository
    ): GetAllFavoriteCitiesUseCase {
        return GetAllFavoriteCitiesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveFavoriteCityUseCase(
        repository: FavoriteCityRepository
    ): SaveFavoriteCityUseCase {
        return SaveFavoriteCityUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCityDetailsUseCase(
        weatherRepository: WeatherRepository
    ): GetCityDetailsUseCase {
        return GetCityDetailsUseCase(weatherRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllWeatherForecastUseCase(
        weatherRepository: WeatherRepository
    ): GetAllWeatherForecastUseCase {
        return GetAllWeatherForecastUseCase(weatherRepository)
    }

    @Provides
    @Singleton
    fun provideGetThemeUseCase(repository: ThemeRepository): GetThemeUseCase {
        return GetThemeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveThemeUseCase(repository: ThemeRepository): SaveThemeUseCase {
        return SaveThemeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideApplyThemeUseCase(repository: ThemeRepository): ApplyThemeUseCase {
        return ApplyThemeUseCase(repository)
    }
}
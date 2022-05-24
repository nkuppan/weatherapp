package com.nkuppan.weatherapp.di

import com.nkuppan.weatherapp.data.repository.FakeFavoriteCityRepository
import com.nkuppan.weatherapp.data.repository.FakeSettingsRepository
import com.nkuppan.weatherapp.data.repository.FakeThemeRepository
import com.nkuppan.weatherapp.data.repository.FakeWeatherRepository
import com.nkuppan.weatherapp.domain.respository.FavoriteCityRepository
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import com.nkuppan.weatherapp.domain.respository.ThemeRepository
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [
        SingletonComponent::class
    ],
    replaces = [
        RepositoryModule::class
    ]
)
object TestRepositoryModule {

    @Provides
    @Singleton
    fun provideTestSettingsRepository(): SettingsRepository {
        return FakeSettingsRepository()
    }

    @Provides
    @Singleton
    fun provideTestThemeRepository(): ThemeRepository {
        return FakeThemeRepository()
    }

    @Provides
    @Singleton
    fun provideTestFavoriteCityRepository(): FavoriteCityRepository {
        return FakeFavoriteCityRepository()
    }

    @Provides
    @Singleton
    fun provideTestWeatherRepository(): WeatherRepository {
        return FakeWeatherRepository()
    }
}
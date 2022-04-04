package com.nkuppan.weatherapp.di

import com.nkuppan.weatherapp.data.datastore.SettingsDataStore
import com.nkuppan.weatherapp.data.datastore.ThemeDataStore
import com.nkuppan.weatherapp.data.db.dao.FavoriteCityDao
import com.nkuppan.weatherapp.data.mapper.*
import com.nkuppan.weatherapp.data.network.OpenWeatherMapApiService
import com.nkuppan.weatherapp.data.respository.FavoriteCityRepositoryImpl
import com.nkuppan.weatherapp.data.respository.OpenWeatherMapRepositoryImpl
import com.nkuppan.weatherapp.data.respository.SettingsRepositoryImpl
import com.nkuppan.weatherapp.data.respository.ThemeRepositoryImpl
import com.nkuppan.weatherapp.domain.respository.FavoriteCityRepository
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import com.nkuppan.weatherapp.domain.respository.ThemeRepository
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import com.nkuppan.weatherapp.domain.utils.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideThemeRepository(
        dataStore: ThemeDataStore,
        dispatchers: AppCoroutineDispatchers
    ): ThemeRepository {
        return ThemeRepositoryImpl(dataStore, dispatchers)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        dataStore: SettingsDataStore,
        dispatchers: AppCoroutineDispatchers
    ): SettingsRepository {
        return SettingsRepositoryImpl(dataStore, dispatchers)
    }

    @Provides
    @Singleton
    fun provideFavoriteCityRepository(
        favoriteCityDao: FavoriteCityDao,
        dispatchers: AppCoroutineDispatchers
    ): FavoriteCityRepository {
        return FavoriteCityRepositoryImpl(
            favoriteCityDao,
            FavoriteEntityToModelMapper(),
            FavoriteModelToEntityMapper(),
            dispatchers
        )
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        service: OpenWeatherMapApiService,
        dispatchers: AppCoroutineDispatchers,
    ): WeatherRepository {
        return OpenWeatherMapRepositoryImpl(
            service,
            CurrentWeatherDtoMapper(),
            HourlyWeatherDtoMapper(),
            DailyWeatherDtoMapper(),
            dispatchers
        )
    }
}
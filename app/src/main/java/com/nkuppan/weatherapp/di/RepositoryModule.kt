package com.nkuppan.weatherapp.di

import android.content.Context
import com.nkuppan.weatherapp.data.datastore.SettingsDataStore
import com.nkuppan.weatherapp.data.datastore.ThemeDataStore
import com.nkuppan.weatherapp.data.network.OpenWeatherMapApiService
import com.nkuppan.weatherapp.data.network.mapper.CurrentWeatherDtoMapper
import com.nkuppan.weatherapp.data.network.mapper.DailyWeatherDtoMapper
import com.nkuppan.weatherapp.data.network.mapper.HourlyWeatherDtoMapper
import com.nkuppan.weatherapp.data.respository.OpenWeatherMapRepositoryImpl
import com.nkuppan.weatherapp.data.respository.SettingsRepositoryImpl
import com.nkuppan.weatherapp.data.respository.ThemeRepositoryImpl
import com.nkuppan.weatherapp.di.AppModule.dataStore
import com.nkuppan.weatherapp.domain.respository.SettingsRepository
import com.nkuppan.weatherapp.domain.respository.ThemeRepository
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import com.nkuppan.weatherapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideThemeDataStore(@ApplicationContext context: Context): ThemeDataStore {
        return ThemeDataStore(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideThemeRepository(dataStore: ThemeDataStore): ThemeRepository {
        return ThemeRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(dataStore: SettingsDataStore): SettingsRepository {
        return SettingsRepositoryImpl(dataStore)
    }

    /*@Provides
    @Singleton
    fun provideWeatherRepository(service: AccWeatherApiService): WeatherRepository {
        return AccuWeatherRepositoryImpl(service)
    }*/

    @Provides
    @Singleton
    fun provideWeatherRepository(service: OpenWeatherMapApiService): WeatherRepository {
        return OpenWeatherMapRepositoryImpl(
            service,
            CurrentWeatherDtoMapper(),
            HourlyWeatherDtoMapper(),
            DailyWeatherDtoMapper()
        )
    }

    @Provides
    @Singleton
    fun provideGetHourlyWeatherForecastUseCase(
        weatherRepository: WeatherRepository
    ): GetHourlyWeatherForecastUseCase {
        return GetHourlyWeatherForecastUseCase(weatherRepository)
    }

    @Provides
    @Singleton
    fun provideGetDailyWeatherForecastUseCase(
        weatherRepository: WeatherRepository
    ): GetDailyWeatherForecastUseCase {
        return GetDailyWeatherForecastUseCase(weatherRepository)
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
}
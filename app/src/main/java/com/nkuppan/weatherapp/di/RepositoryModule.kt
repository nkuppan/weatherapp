package com.nkuppan.weatherapp.di

import android.content.Context
import com.nkuppan.weatherapp.data.datastore.ThemeDataStore
import com.nkuppan.weatherapp.data.network.WeatherApiService
import com.nkuppan.weatherapp.data.network.model.WeatherDtoMapper
import com.nkuppan.weatherapp.data.respository.ThemeRepositoryImpl
import com.nkuppan.weatherapp.data.respository.WeatherRepositoryImpl
import com.nkuppan.weatherapp.di.AppModule.dataStore
import com.nkuppan.weatherapp.domain.respository.ThemeRepository
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import com.nkuppan.weatherapp.domain.usecase.GetDailyWeatherForecastUseCase
import com.nkuppan.weatherapp.domain.usecase.GetHourlyWeatherForecastUseCase
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
    fun provideWeatherDtoMapper(): WeatherDtoMapper {
        return WeatherDtoMapper()
    }

    @Provides
    @Singleton
    fun provideThemeDataStore(@ApplicationContext context: Context): ThemeDataStore {
        return ThemeDataStore(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideThemeRepository(
        dataStore: ThemeDataStore
    ): ThemeRepository {
        return ThemeRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        service: WeatherApiService,
        mapper: WeatherDtoMapper
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            service,
            mapper
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
}
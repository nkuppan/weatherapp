package com.nkuppan.weatherapp.di

import android.content.Context
import com.nkuppan.weatherapp.data.datastore.ThemeDataStore
import com.nkuppan.weatherapp.data.network.AccWeatherApiService
import com.nkuppan.weatherapp.data.network.model.CityDtoMapper
import com.nkuppan.weatherapp.data.network.model.DailyWeatherDtoMapper
import com.nkuppan.weatherapp.data.network.model.HourlyWeatherDtoMapper
import com.nkuppan.weatherapp.data.respository.ThemeRepositoryImpl
import com.nkuppan.weatherapp.data.respository.AccuWeatherRepositoryImpl
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
    fun provideWeatherDtoMapper(): DailyWeatherDtoMapper {
        return DailyWeatherDtoMapper()
    }

    @Provides
    @Singleton
    fun provideHourlyWeatherDtoMapper(): HourlyWeatherDtoMapper {
        return HourlyWeatherDtoMapper()
    }

    @Provides
    @Singleton
    fun provideCityDtoMapper(): CityDtoMapper {
        return CityDtoMapper()
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
        service: AccWeatherApiService,
        dailyWeatherDtoMapper: DailyWeatherDtoMapper,
        hourlyWeatherDtoMapper: HourlyWeatherDtoMapper,
        cityDtoMapper: CityDtoMapper,
    ): WeatherRepository {
        return AccuWeatherRepositoryImpl(
            service,
            dailyWeatherDtoMapper,
            hourlyWeatherDtoMapper,
            cityDtoMapper
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
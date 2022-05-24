package com.nkuppan.weatherapp.di

import com.nkuppan.weatherapp.data.network.AccWeatherApiService
import com.nkuppan.weatherapp.data.network.OpenWeatherMapApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply { interceptors.forEach(::addInterceptor) }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideAccWeatherApiService(okHttpClient: OkHttpClient): AccWeatherApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AccWeatherApiService.BASE_URL)
            .build().create()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherMapApiService(okHttpClient: OkHttpClient): OpenWeatherMapApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(OpenWeatherMapApiService.BASE_URL)
            .build().create()
    }
}
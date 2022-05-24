package com.nkuppan.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object InterceptorModule {

    /**
     * Initially thought of adding mock response provider here to complete the task without backend.
     **/
    @Provides
    @Singleton
    @IntoSet
    fun provideHttpLoggerInterceptor(): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }
}
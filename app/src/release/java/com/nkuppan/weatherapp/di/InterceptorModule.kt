package com.nkuppan.comic.di

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

    @Provides
    @Singleton
    @IntoSet
    fun provideHttpLoggerInterceptor(): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.NONE
        return logger
    }
}
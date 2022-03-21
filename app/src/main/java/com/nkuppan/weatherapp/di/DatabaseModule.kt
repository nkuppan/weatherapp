package com.nkuppan.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.nkuppan.weatherapp.data.db.WeatherAppDatabase
import com.nkuppan.weatherapp.data.db.dao.FavoriteCityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    private const val FOREX_DB_NAME = "weatherapp.db"

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): WeatherAppDatabase {
        return Room
            .databaseBuilder(
                context.applicationContext,
                WeatherAppDatabase::class.java,
                FOREX_DB_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteCityDao(dataBase: WeatherAppDatabase): FavoriteCityDao {
        return dataBase.favoriteCityDao()
    }
}
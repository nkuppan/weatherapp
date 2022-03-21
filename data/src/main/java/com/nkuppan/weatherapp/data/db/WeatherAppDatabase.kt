package com.nkuppan.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nkuppan.weatherapp.data.db.dao.FavoriteCityDao
import com.nkuppan.weatherapp.data.db.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherAppDatabase : RoomDatabase() {

    abstract fun favoriteCityDao(): FavoriteCityDao
}
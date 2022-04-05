package com.nkuppan.weatherapp.data.db.dao

import androidx.room.*
import com.nkuppan.weatherapp.data.db.entity.FavoriteEntity

@Dao
interface FavoriteCityDao {

    @Query("SELECT * FROM favorite WHERE is_favorite=1")
    fun getAllFavorites(): List<FavoriteEntity>?

    @Query("SELECT * FROM favorite WHERE name=:name")
    fun getFavorite(name: String): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFavorite(favorite: List<FavoriteEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity?): Long

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity?)
}
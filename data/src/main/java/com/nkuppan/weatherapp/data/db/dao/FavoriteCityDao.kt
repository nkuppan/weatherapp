package com.nkuppan.weatherapp.data.db.dao

import androidx.room.*
import com.nkuppan.weatherapp.data.db.entity.FavoriteEntity

@Dao
interface FavoriteCityDao {

    @Query("SELECT * FROM favorite WHERE is_favorite=1")
    suspend fun getAllFavorites(): List<FavoriteEntity>?

    @Query("SELECT * FROM favorite WHERE name=:name")
    suspend fun getFavorite(name: String): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFavorite(favorite: List<FavoriteEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity?): Long

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity?)
}
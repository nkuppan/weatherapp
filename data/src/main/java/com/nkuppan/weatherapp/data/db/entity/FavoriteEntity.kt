package com.nkuppan.weatherapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class FavoriteEntity {
    @PrimaryKey
    @ColumnInfo(name = "name")
    var name = ""

    @ColumnInfo(name = "country")
    var country: String = ""

    @ColumnInfo(name = "latitude")
    var latitude: Double = 0.0

    @ColumnInfo(name = "longitude")
    var longitude: Double = 0.0

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = true
}
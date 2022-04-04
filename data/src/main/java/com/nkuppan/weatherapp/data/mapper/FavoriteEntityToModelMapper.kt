package com.nkuppan.weatherapp.data.mapper

import com.nkuppan.weatherapp.data.db.entity.FavoriteEntity
import com.nkuppan.weatherapp.domain.mapper.Mapper
import com.nkuppan.weatherapp.domain.model.City

class FavoriteEntityToModelMapper : Mapper<FavoriteEntity, City> {

    override fun convert(fromObject: FavoriteEntity): City {
        return City(
            fromObject.name,
            "",
            1,
            fromObject.country,
            "",
            fromObject.latitude,
            fromObject.longitude,
            fromObject.isFavorite
        )
    }
}

class FavoriteModelToEntityMapper : Mapper<City, FavoriteEntity> {

    override fun convert(fromObject: City): FavoriteEntity {
        return FavoriteEntity().apply {
            name = fromObject.name
            country = fromObject.country
            latitude = fromObject.latitude ?: 0.0
            longitude = fromObject.longitude ?: 0.0
            isFavorite = fromObject.isFavorite
        }
    }
}
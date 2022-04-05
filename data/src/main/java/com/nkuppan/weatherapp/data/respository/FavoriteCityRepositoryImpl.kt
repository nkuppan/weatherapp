package com.nkuppan.weatherapp.data.respository

import com.nkuppan.weatherapp.data.db.dao.FavoriteCityDao
import com.nkuppan.weatherapp.data.mapper.FavoriteEntityToModelMapper
import com.nkuppan.weatherapp.data.mapper.FavoriteModelToEntityMapper
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.respository.FavoriteCityRepository
import com.nkuppan.weatherapp.domain.utils.AppCoroutineDispatchers
import kotlinx.coroutines.withContext

class FavoriteCityRepositoryImpl(
    private val favoriteCityDao: FavoriteCityDao,
    private val favoriteEntityToModelMapper: FavoriteEntityToModelMapper,
    private val favoriteModelToEntityMapper: FavoriteModelToEntityMapper,
    private val dispatcher: AppCoroutineDispatchers
) : FavoriteCityRepository {

    override suspend fun addFavoriteCity(favoriteCity: City): Unit =
        withContext(dispatcher.io) {
            favoriteCityDao.insertFavorite(favoriteModelToEntityMapper.convert(favoriteCity))
        }

    override suspend fun getAllFavoriteCities(): Resource<List<City>> =
        withContext(dispatcher.io) {
            return@withContext try {
                Resource.Success(
                    favoriteCityDao.getAllFavorites()?.map {
                        favoriteEntityToModelMapper.convert(it)
                    } ?: emptyList()
                )
            } catch (exception: Exception) {
                Resource.Error(exception)
            }
        }
}
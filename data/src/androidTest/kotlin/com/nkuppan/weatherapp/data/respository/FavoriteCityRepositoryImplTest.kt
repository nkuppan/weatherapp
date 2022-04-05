package com.nkuppan.weatherapp.data.respository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.nkuppan.weatherapp.data.db.WeatherAppDatabase
import com.nkuppan.weatherapp.data.db.dao.FavoriteCityDao
import com.nkuppan.weatherapp.data.db.entity.FavoriteEntity
import com.nkuppan.weatherapp.utils.BaseCoroutineTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavoriteCityRepositoryImplTest : BaseCoroutineTest() {

    private lateinit var favoriteCityDao: FavoriteCityDao
    private lateinit var weatherAppDatabase: WeatherAppDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    override fun onCreate() {
        super.onCreate()

        weatherAppDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherAppDatabase::class.java
        ).allowMainThreadQueries().build()

        favoriteCityDao = weatherAppDatabase.favoriteCityDao()
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherAppDatabase.close()
    }

    @Test
    fun validateFavoriteCityTest() {
        val id = favoriteCityDao.insertFavorite(fakeFavoriteEntity)
        Truth.assertThat(id).isNotEqualTo(-1)
        val favoriteCityData = favoriteCityDao.getFavorite(fakeFavoriteEntity.name)
        Truth.assertThat(favoriteCityData).isNotNull()
    }

    @Test
    fun validateFavoriteCityInsertReplaceStrategy() {
        val id = favoriteCityDao.insertFavorite(fakeFavoriteEntity)
        Truth.assertThat(id).isNotEqualTo(-1)

        val newId = favoriteCityDao.insertFavorite(updatedFakeFavoriteEntity)
        Truth.assertThat(newId).isNotEqualTo(-1)

        val favoriteCityData = favoriteCityDao.getFavorite(updatedFakeFavoriteEntity.name)
        Truth.assertThat(favoriteCityData).isNotNull()
        Truth.assertThat(favoriteCityData!!.name).isEqualTo(updatedFakeFavoriteEntity.name)
        Truth.assertThat(favoriteCityData.country).isEqualTo(updatedFakeFavoriteEntity.country)
        Truth.assertThat(favoriteCityData.latitude).isEqualTo(updatedFakeFavoriteEntity.latitude)
        Truth.assertThat(favoriteCityData.longitude).isEqualTo(updatedFakeFavoriteEntity.longitude)
        Truth.assertThat(favoriteCityData.isFavorite).isEqualTo(updatedFakeFavoriteEntity.isFavorite)
    }

    @Test
    fun validateFavoriteCityInsertAndRetrieve() {
        val id = favoriteCityDao.insertFavorite(fakeFavoriteEntity)
        Truth.assertThat(id).isNotEqualTo(-1)

        val favoriteCityData = favoriteCityDao.getFavorite(fakeFavoriteEntity.name)
        Truth.assertThat(favoriteCityData).isNotNull()
        Truth.assertThat(favoriteCityData!!.name).isEqualTo(fakeFavoriteEntity.name)
        Truth.assertThat(favoriteCityData.country).isEqualTo(fakeFavoriteEntity.country)
        Truth.assertThat(favoriteCityData.latitude).isEqualTo(fakeFavoriteEntity.latitude)
        Truth.assertThat(favoriteCityData.longitude).isEqualTo(fakeFavoriteEntity.longitude)
        Truth.assertThat(favoriteCityData.isFavorite).isEqualTo(fakeFavoriteEntity.isFavorite)
    }

    @Test
    fun validateFavoriteCityInsertDeleteRetrieve() {
        val id = favoriteCityDao.insertFavorite(fakeFavoriteEntity)
        Truth.assertThat(id).isNotEqualTo(-1)

        var favoriteCityData = favoriteCityDao.getFavorite(fakeFavoriteEntity.name)
        Truth.assertThat(favoriteCityData).isNotNull()
        Truth.assertThat(favoriteCityData!!.name).isEqualTo(fakeFavoriteEntity.name)
        Truth.assertThat(favoriteCityData.country).isEqualTo(fakeFavoriteEntity.country)
        Truth.assertThat(favoriteCityData.latitude).isEqualTo(fakeFavoriteEntity.latitude)
        Truth.assertThat(favoriteCityData.longitude).isEqualTo(fakeFavoriteEntity.longitude)
        Truth.assertThat(favoriteCityData.isFavorite).isEqualTo(fakeFavoriteEntity.isFavorite)

        favoriteCityDao.deleteFavorite(fakeFavoriteEntity)

        favoriteCityData = favoriteCityDao.getFavorite(fakeFavoriteEntity.name)
        Truth.assertThat(favoriteCityData).isNull()
    }


    companion object {

        private const val CITY_NAME = "London"
        private const val CITY_COUNTRY = "GB"

        private val fakeFavoriteEntity = FavoriteEntity().apply {
            name = CITY_NAME
            country = CITY_COUNTRY
            latitude = 0.0
            longitude = 0.0
            isFavorite = true
        }

        private val updatedFakeFavoriteEntity = FavoriteEntity().apply {
            name = CITY_NAME
            country = CITY_COUNTRY
            latitude = 0.0
            longitude = 0.0
            isFavorite = true
        }
    }
}
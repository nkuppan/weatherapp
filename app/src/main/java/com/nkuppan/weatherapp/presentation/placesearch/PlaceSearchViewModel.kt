package com.nkuppan.weatherapp.presentation.placesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.extention.Event
import com.nkuppan.weatherapp.core.ui.viewmodel.BaseViewModel
import com.nkuppan.weatherapp.domain.extentions.isValidQueryString
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.usecase.favorite.GetAllFavoriteCitiesUseCase
import com.nkuppan.weatherapp.domain.usecase.weather.GetCityDetailsUseCase
import com.nkuppan.weatherapp.domain.usecase.favorite.SaveFavoriteCityUseCase
import com.nkuppan.weatherapp.domain.usecase.settings.SaveSelectedCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceSearchViewModel @Inject constructor(
    private val getCityDetailsUseCase: GetCityDetailsUseCase,
    private val getAllFavoriteCitiesUseCase: GetAllFavoriteCitiesUseCase,
    private val saveSelectedCityUseCase: SaveSelectedCityUseCase,
    private val saveFavoriteCityUseCase: SaveFavoriteCityUseCase,
) : BaseViewModel() {

    val queryString: MutableLiveData<String> = MutableLiveData()

    private val _places = MutableLiveData<List<City>>()
    val places: LiveData<List<City>> = _places

    private val _placeSelected = MutableLiveData<Event<City>>()
    val placeSelected: LiveData<Event<City>> = _placeSelected

    private var searchJob: Job? = null

    private var isPlaceAvailable = false

    fun processQuery(): Boolean {

        val query = queryString.value

        return if (query.isValidQueryString()) {
            searchPlaces(query)
            true
        } else {
            _errorMessage.value = R.string.enter_valid_query_string
            false
        }
    }

    fun searchFavorites() {

        searchJob?.cancel()

        _isLoading.value = true

        searchJob = viewModelScope.launch {

            when (val response = getAllFavoriteCitiesUseCase.invoke()) {
                is Resource.Success -> {
                    val data = response.data
                    _places.value = data.ifEmpty {
                        emptyList()
                    }
                }
                is Resource.Error -> {
                    _errorMessage.value = R.string.unable_to_fetch_data
                }
            }

            _isLoading.value = false
        }
    }

    private fun searchPlaces(placeName: String?) {

        placeName ?: return

        searchJob?.cancel()

        _isLoading.value = true

        searchJob = viewModelScope.launch {

            when (val response = getCityDetailsUseCase.invoke(placeName)) {
                is Resource.Success -> {
                    val data = response.data
                    _places.value = data.ifEmpty {
                        emptyList()
                    }
                    isPlaceAvailable = true
                }
                is Resource.Error -> {
                    _errorMessage.value = R.string.unable_to_fetch_data
                }
            }

            _isLoading.value = false
        }
    }

    fun saveSelectedCity(city: City) {

        viewModelScope.launch {
            when (saveSelectedCityUseCase.invoke(city)) {
                is Resource.Error -> {
                    _errorMessage.value = R.string.city_selection_failed
                }
                is Resource.Success -> {
                    _placeSelected.value = Event(city)
                }
            }
        }
    }

    fun saveFavoriteCity(city: City) {
        viewModelScope.launch {
            saveFavoriteCityUseCase.invoke(city)
            //Refreshing the favorite value if the places is not loaded from server
            if (!isPlaceAvailable) {
                searchFavorites()
            }
        }
    }
}
package com.nkuppan.weatherapp.presentation.placesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.extention.Event
import com.nkuppan.weatherapp.domain.extentions.isValidQueryString
import com.nkuppan.weatherapp.domain.model.City
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.utils.UiText
import com.nkuppan.weatherapp.domain.usecase.favorite.GetAllFavoriteCitiesUseCase
import com.nkuppan.weatherapp.domain.usecase.favorite.SaveFavoriteCityUseCase
import com.nkuppan.weatherapp.domain.usecase.settings.SaveSelectedCityUseCase
import com.nkuppan.weatherapp.domain.usecase.weather.GetCityDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceSearchViewModel @Inject constructor(
    private val getCityDetailsUseCase: GetCityDetailsUseCase,
    private val getAllFavoriteCitiesUseCase: GetAllFavoriteCitiesUseCase,
    private val saveSelectedCityUseCase: SaveSelectedCityUseCase,
    private val saveFavoriteCityUseCase: SaveFavoriteCityUseCase,
) : ViewModel() {

    val queryString = MutableStateFlow("")

    private val _errorMessage = Channel<UiText>()
    val errorMessage = _errorMessage.receiveAsFlow()

    private val _places = Channel<List<City>>()
    val places = _places.receiveAsFlow()

    private val _placeSelected = MutableLiveData<Event<City>>()
    val placeSelected: LiveData<Event<City>> = _placeSelected

    private var searchJob: Job? = null

    private var isPlaceAvailable = false

    fun processQuery(): Boolean {

        val query = queryString.value

        viewModelScope.launch {
            if (query.isValidQueryString()) {
                searchPlaces(query)
            } else {
                _errorMessage.send(
                    UiText.StringResource(R.string.enter_valid_query_string)
                )
            }
        }

        return true
    }

    fun searchFavorites() {

        searchJob?.cancel()

        searchJob = viewModelScope.launch {

            when (val response = getAllFavoriteCitiesUseCase.invoke()) {
                is Resource.Success -> {
                    val data = response.data
                    _places.send(
                        data.ifEmpty {
                            emptyList()
                        }
                    )
                }
                is Resource.Error -> {
                    _errorMessage.send(
                        UiText.StringResource(R.string.unable_to_fetch_data)
                    )
                }
            }
        }
    }

    private fun searchPlaces(placeName: String?) {

        placeName ?: return

        searchJob?.cancel()

        searchJob = viewModelScope.launch {

            when (val response = getCityDetailsUseCase.invoke(placeName)) {
                is Resource.Success -> {
                    val data = response.data
                    _places.send(
                        data.ifEmpty {
                            emptyList()
                        }
                    )
                    isPlaceAvailable = true
                }
                is Resource.Error -> {
                    _errorMessage.send(
                        UiText.StringResource(R.string.unable_to_fetch_data)
                    )
                }
            }
        }
    }

    fun saveSelectedCity(city: City) {

        viewModelScope.launch {
            when (saveSelectedCityUseCase.invoke(city)) {
                is Resource.Error -> {
                    _errorMessage.send(
                        UiText.StringResource(R.string.city_selection_failed)
                    )
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
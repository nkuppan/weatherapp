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
import com.nkuppan.weatherapp.domain.usecase.GetCityDetailsUseCase
import com.nkuppan.weatherapp.domain.usecase.SaveSelectedCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceSearchViewModel @Inject constructor(
    private val getCityDetailsUseCase: GetCityDetailsUseCase,
    private val saveSelectedCityUseCase: SaveSelectedCityUseCase,
) : BaseViewModel() {

    val queryString: MutableLiveData<String> = MutableLiveData()

    private val _places = MutableLiveData<List<City>>()
    val places: LiveData<List<City>> = _places

    private var searchJob: Job? = null

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

    private fun searchPlaces(placeName: String?) {

        placeName ?: return

        searchJob?.cancel()

        _isLoading.value = true

        searchJob = viewModelScope.launch {

            when (val response = getCityDetailsUseCase.invoke(placeName)) {
                is Resource.Success -> {
                    if (response.data.isNotEmpty()) {
                        _places.value = response.data!!
                    } else {
                        _errorMessage.value = (R.string.city_name_is_invalid)
                    }
                }
                is Resource.Error -> {
                    _errorMessage.value = (R.string.unable_to_fetch_data)
                }
            }

            _isLoading.value = false
        }
    }

    fun saveSelectedCity(city: City) {

        viewModelScope.launch {
            saveSelectedCityUseCase.invoke(city)
            _success.value = Event(Unit)
        }
    }
}
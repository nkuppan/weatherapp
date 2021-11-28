package com.nkuppan.weatherapp.presentation.placesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.ui.viewmodel.BaseViewModel
import com.nkuppan.weatherapp.domain.extentions.isValidQueryString
import com.nkuppan.weatherapp.domain.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceSearchViewModel @Inject constructor() : BaseViewModel() {

    val queryString: MutableLiveData<String> = MutableLiveData()

    private val _places = MutableLiveData<List<City>>()
    val places: LiveData<List<City>> = _places

    fun processQuery(): Boolean {

        val query = queryString.value

        return if (query.isValidQueryString()) {
            //TODO start place search
            true
        } else {
            _errorMessage.value = R.string.enter_valid_query_string
            false
        }
    }
}
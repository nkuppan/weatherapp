package com.nkuppan.weatherapp.core.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nkuppan.weatherapp.core.extention.Event

/**
 * Wrapper viewmodel class and this will be inherited by all viewmodel in the project
 */
open class BaseViewModel : ViewModel() {

    protected val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _success = MutableLiveData<Event<Unit>>()
    val success: LiveData<Event<Unit>> = _success
}

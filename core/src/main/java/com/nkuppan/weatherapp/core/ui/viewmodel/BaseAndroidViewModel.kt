package com.nkuppan.weatherapp.core.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nkuppan.weatherapp.core.extention.Event

/**
 * Wrapper viewmodel class and this will be inherited by all viewmodel in the project
 */
open class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    protected val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _success = MutableLiveData<Event<Unit>>()
    val success: LiveData<Event<Unit>> = _success
}

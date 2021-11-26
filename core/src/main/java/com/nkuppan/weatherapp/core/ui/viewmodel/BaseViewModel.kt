package com.nkuppan.weatherapp.core.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Wrapper viewmodel class and this will be inherited by all viewmodel in the project
 */
open class BaseViewModel : ViewModel() {

    protected val _errorMessage = MutableSharedFlow<Int>()
    val errorMessage: SharedFlow<Int> = _errorMessage.asSharedFlow()

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
}

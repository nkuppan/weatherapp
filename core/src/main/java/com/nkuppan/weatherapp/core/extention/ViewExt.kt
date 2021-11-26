package com.nkuppan.weatherapp.core.extention

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, length).show()
}

fun View.showSnackbar(@StringRes message: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, this.resources.getString(message), length).show()
}
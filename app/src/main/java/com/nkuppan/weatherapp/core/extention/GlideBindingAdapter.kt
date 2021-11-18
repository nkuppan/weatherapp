package com.nkuppan.weatherapp.core.extention

import android.webkit.URLUtil
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.data.network.AccWeatherApiService


private fun loadErrorImage(imageView: ImageView) {
    Glide
        .with(imageView.context)
        .load(R.drawable.ic_error)
        .placeholder(R.drawable.image_place_holder)
        .into(imageView)
}

@BindingAdapter("app:loadWeatherImageURL")
fun loadWeatherImageURL(imageView: ImageView, iconName: String?) {

    if (iconName.isNullOrBlank()) {
        loadErrorImage(imageView)
        return
    }

    val newURL = String.format(AccWeatherApiService.BASE_URL, iconName)

    if (URLUtil.isValidUrl(newURL)) {

        Glide
            .with(imageView.context)
            .setDefaultRequestOptions(
                RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
            )
            .load(newURL)
            .error(R.drawable.ic_error)
            .placeholder(R.drawable.image_place_holder)
            .into(imageView)
    } else {
        loadErrorImage(imageView)
    }
}
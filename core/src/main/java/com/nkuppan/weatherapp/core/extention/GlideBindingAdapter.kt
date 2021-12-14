package com.nkuppan.weatherapp.core.extention

import android.webkit.URLUtil
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.nkuppan.weatherapp.core.R


private fun loadErrorImage(imageView: ImageView) {
    Glide
        .with(imageView.context)
        .load(R.drawable.ic_error)
        .placeholder(R.drawable.image_place_holder)
        .into(imageView)
}

@BindingAdapter("app:loadWeatherImageURL")
fun loadWeatherImageURL(imageView: ImageView, networkURL: String?) {

    if (networkURL.isNullOrBlank() || !URLUtil.isValidUrl(networkURL)) {
        loadErrorImage(imageView)
        return
    }
    Glide
        .with(imageView.context)
        .setDefaultRequestOptions(
            RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
        )
        .load(networkURL)
        .error(R.drawable.ic_error)
        .placeholder(R.drawable.image_place_holder)
        .into(imageView)
}
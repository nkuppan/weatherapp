package com.nkuppan.weatherapp.presentation.weatherdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkuppan.weatherapp.databinding.ListItemHourlyForecastBinding


class HourlyForecastAdapter : ListAdapter<WeatherUIModel, HourlyForecastViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder =
        HourlyForecastViewHolder(
            ListItemHourlyForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        holder.item.viewModel = getItem(position)
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherUIModel>() {
            override fun areItemsTheSame(oldItem: WeatherUIModel, newItem: WeatherUIModel) =
                oldItem.date == newItem.date

            override fun areContentsTheSame(
                oldItem: WeatherUIModel,
                newItem: WeatherUIModel
            ) = oldItem == newItem
        }
    }
}

class HourlyForecastViewHolder(val item: ListItemHourlyForecastBinding) :
    RecyclerView.ViewHolder(item.root)
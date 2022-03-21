package com.nkuppan.weatherapp.presentation.weatherdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkuppan.weatherapp.databinding.ListItemDailyForecastBinding

class DailyForecastAdapter : ListAdapter<WeatherUIModel, DailyForecastViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder =
        DailyForecastViewHolder(
            ListItemDailyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
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

class DailyForecastViewHolder(val item: ListItemDailyForecastBinding) :
    RecyclerView.ViewHolder(item.root)
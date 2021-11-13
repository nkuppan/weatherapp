package com.nkuppan.weatherapp.presentation.weatherdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkuppan.weatherapp.databinding.ListItemDailyForecastBinding
import com.nkuppan.weatherapp.domain.model.Weather


class HourlyForecastAdapter : ListAdapter<Weather, HourlyForecastViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder =
        HourlyForecastViewHolder(
            ListItemDailyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        holder.item.viewModel = getItem(position)
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Weather>() {
            override fun areItemsTheSame(oldItem: Weather, newItem: Weather) =
                oldItem.date == newItem.date

            override fun areContentsTheSame(
                oldItem: Weather,
                newItem: Weather
            ) = oldItem == newItem
        }
    }
}

class HourlyForecastViewHolder(val item: ListItemDailyForecastBinding) :
    RecyclerView.ViewHolder(item.root)
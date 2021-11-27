package com.nkuppan.weatherapp.presentation.weatherdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkuppan.weatherapp.databinding.ListItemCurrentForecastBinding
import com.nkuppan.weatherapp.databinding.ListItemDailyForecastBinding
import com.nkuppan.weatherapp.databinding.ListItemHourlyListViewBinding
import com.nkuppan.weatherapp.domain.model.WeatherUIModel


class WeatherForecastAdapter :
    ListAdapter<WeatherUIModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                CurrentWeatherForecastViewHolder(
                    ListItemCurrentForecastBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            1 -> {
                HourlyForecastListViewHolder(
                    ListItemHourlyListViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            2 -> {
                DailyForecastViewHolder(
                    ListItemDailyForecastBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw IllegalArgumentException("Invalid type inputs")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.ordinal
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val response = getItem(position)

        when (holder) {
            is CurrentWeatherForecastViewHolder -> {
                holder.item.viewModel = response.weather[0]
            }
            is HourlyForecastListViewHolder -> {
                holder.item.hourlyForecastView.apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    val hourlyForecastAdapter = HourlyForecastAdapter()
                    adapter = hourlyForecastAdapter
                    hourlyForecastAdapter.submitList(response.weather)
                }
            }
            is DailyForecastViewHolder -> {
                holder.item.viewModel = response.weather[0]
            }
            else -> {
                throw IllegalArgumentException("Invalid type inputs")
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherUIModel>() {
            override fun areItemsTheSame(oldItem: WeatherUIModel, newItem: WeatherUIModel) =
                oldItem.weather.size == newItem.weather.size

            override fun areContentsTheSame(
                oldItem: WeatherUIModel,
                newItem: WeatherUIModel
            ) = oldItem == newItem
        }
    }
}

class CurrentWeatherForecastViewHolder(val item: ListItemCurrentForecastBinding) :
    RecyclerView.ViewHolder(item.root)

class HourlyForecastListViewHolder(val item: ListItemHourlyListViewBinding) :
    RecyclerView.ViewHolder(item.root)
package com.nkuppan.weatherapp.presentation.placesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkuppan.weatherapp.databinding.ListItemPlaceBinding
import com.nkuppan.weatherapp.domain.model.City

class PlaceListAdapter(private val callback: (Int, City) -> Unit) :
    ListAdapter<City, PlaceViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder =
        PlaceViewHolder(
            ListItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        getItem(position)?.let { city ->
            holder.item.viewModel = city
            holder.item.root.setOnClickListener {
                callback.invoke(1, city)
            }
            holder.item.favorite.setOnClickListener {
                //Changing the favorite icon
                city.isFavorite = !city.isFavorite
                notifyItemChanged(position)
                callback.invoke(2, city)
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<City>() {
            override fun areItemsTheSame(oldItem: City, newItem: City) =
                oldItem.key == newItem.key

            override fun areContentsTheSame(
                oldItem: City,
                newItem: City
            ) = oldItem == newItem
        }
    }
}

class PlaceViewHolder(val item: ListItemPlaceBinding) :
    RecyclerView.ViewHolder(item.root)
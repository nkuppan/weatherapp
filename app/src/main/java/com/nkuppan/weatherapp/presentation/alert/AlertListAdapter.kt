package com.nkuppan.weatherapp.presentation.alert

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkuppan.weatherapp.databinding.ListItemAlertBinding

class AlertListAdapter : ListAdapter<AlertUIModel, AlertViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder =
        AlertViewHolder(
            ListItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        getItem(position)?.let { alert ->
            holder.item.viewModel = alert
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AlertUIModel>() {
            override fun areItemsTheSame(oldItem: AlertUIModel, newItem: AlertUIModel) =
                oldItem.date == newItem.date

            override fun areContentsTheSame(
                oldItem: AlertUIModel,
                newItem: AlertUIModel
            ) = oldItem == newItem
        }
    }
}

class AlertViewHolder(val item: ListItemAlertBinding) :
    RecyclerView.ViewHolder(item.root)
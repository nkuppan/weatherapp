package com.nkuppan.weatherapp.presentation.weathertab

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nkuppan.weatherapp.presentation.weatherdetails.NewWeatherDetailFragment

class WeatherTabAdapter(
    fragment: Fragment,
    private val cityNames: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return cityNames.size
    }

    override fun createFragment(position: Int): Fragment {
        return NewWeatherDetailFragment.newInstance(cityNames[position])
    }
}
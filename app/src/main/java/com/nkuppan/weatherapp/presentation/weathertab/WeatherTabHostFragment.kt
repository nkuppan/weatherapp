package com.nkuppan.weatherapp.presentation.weathertab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.extention.autoCleared
import com.nkuppan.weatherapp.core.ui.fragment.BaseFragment
import com.nkuppan.weatherapp.databinding.FragmentForecastTabHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherTabHostFragment : BaseFragment() {

    private var viewBinding: FragmentForecastTabHostBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentForecastTabHostBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityName = listOf(
            getString(R.string.chennai),
            getString(R.string.rio_de_jeneiro),
            getString(R.string.beijing),
            getString(R.string.los_angeles)
        )

        viewBinding.forecastViewPager.adapter = WeatherTabAdapter(this, cityName)

        TabLayoutMediator(viewBinding.tabLayout, viewBinding.forecastViewPager) { tab, position ->
            tab.text = cityName[position]
        }.attach()
    }
}
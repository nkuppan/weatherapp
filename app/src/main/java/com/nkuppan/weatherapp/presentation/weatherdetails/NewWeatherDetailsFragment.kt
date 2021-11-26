package com.nkuppan.weatherapp.presentation.weatherdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nkuppan.weatherapp.core.extention.autoCleared
import com.nkuppan.weatherapp.core.ui.fragment.BaseFragment
import com.nkuppan.weatherapp.databinding.FragmentForecastDetailsBinding

class NewWeatherDetailFragment : BaseFragment() {

    private var viewBinding: FragmentForecastDetailsBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
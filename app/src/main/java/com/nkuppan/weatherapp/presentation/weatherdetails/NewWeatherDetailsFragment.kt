package com.nkuppan.weatherapp.presentation.weatherdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nkuppan.weatherapp.core.extention.autoCleared
import com.nkuppan.weatherapp.core.ui.fragment.BaseFragment
import com.nkuppan.weatherapp.databinding.FragmentForecastDetailsNewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewWeatherDetailFragment : BaseFragment() {

    private var cityName: String = "London"

    private var viewBinding: FragmentForecastDetailsNewBinding by autoCleared()

    private var weatherForecastAdapter: WeatherForecastAdapter by autoCleared()

    private var viewModel: WeatherDetailsViewModel by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentForecastDetailsNewBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WeatherDetailsViewModel::class.java)

        initializeData()

        initializeRefreshView()

        initializeDailyForecastView()

        initializeObservers()
    }

    private fun initializeRefreshView() {
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchWeatherInfo(cityName, fetchAllDataInOnce = true)
        }
    }

    private fun initializeData() {

        arguments?.getString(CITY_NAME)?.let {
            cityName = it
        }

        viewBinding.place.text = cityName
    }

    private fun initializeObservers() {

        viewModel.isLoading.observe(viewLifecycleOwner) {
            viewBinding.swipeRefreshLayout.isRefreshing = it
        }

        viewModel.allWeatherInfo.observe(viewLifecycleOwner) {
            weatherForecastAdapter.submitList(it)
        }

        viewModel.fetchWeatherInfo(cityName, fetchAllDataInOnce = true)
    }

    private fun initializeDailyForecastView() {
        weatherForecastAdapter = WeatherForecastAdapter()
        viewBinding.weatherDataRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewBinding.weatherDataRecyclerView.setHasFixedSize(true)
        viewBinding.weatherDataRecyclerView.adapter = weatherForecastAdapter
    }

    companion object {
        private const val CITY_NAME = "city_name"
    }
}
package com.nkuppan.weatherapp.presentation.weatherdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nkuppan.weatherapp.core.extention.autoCleared
import com.nkuppan.weatherapp.core.extention.showSnackbar
import com.nkuppan.weatherapp.core.ui.fragment.BaseFragment
import com.nkuppan.weatherapp.databinding.FragmentForecastDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherDetailsFragment : BaseFragment() {

    private var cityName: String = "London"

    private var viewBinding: FragmentForecastDetailsBinding by autoCleared()

    private var dailyForecastAdapter: DailyForecastAdapter by autoCleared()

    private var hourlyForecastAdapter: HourlyForecastAdapter by autoCleared()

    private var viewModel: WeatherDetailsViewModel by autoCleared()

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

        viewModel = ViewModelProvider(this).get(WeatherDetailsViewModel::class.java)

        initializeData()

        initializeRefreshView()

        initializeAdapters()

        initializeObservers()
    }

    private fun initializeRefreshView() {
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchWeatherInfo(cityName)
        }
    }

    private fun initializeData() {
        arguments?.getString(CITY_NAME)?.let {
            cityName = it
        }
    }

    private fun initializeObservers() {

        viewModel.isLoading.observe(viewLifecycleOwner) {
            viewBinding.swipeRefreshLayout.isRefreshing = it
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            viewBinding.swipeRefreshLayout.showSnackbar(it)
        }

        viewModel.hourlyForecastInfo.observe(viewLifecycleOwner) {
            hourlyForecastAdapter.submitList(it)
        }

        viewModel.dailyForecastInfo.observe(viewLifecycleOwner) {
            dailyForecastAdapter.submitList(it)
        }

        viewModel.fetchWeatherInfo(cityName)
    }

    private fun initializeAdapters() {

        initializeHourlyForecastView()

        initializeDailyForecastView()
    }

    private fun initializeDailyForecastView() {
        dailyForecastAdapter = DailyForecastAdapter()
        viewBinding.dailyForecastView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewBinding.dailyForecastView.setHasFixedSize(true)
        viewBinding.dailyForecastView.adapter = dailyForecastAdapter
    }

    private fun initializeHourlyForecastView() {
        hourlyForecastAdapter = HourlyForecastAdapter()
        viewBinding.hourlyForecastView.setHasFixedSize(true)
        viewBinding.hourlyForecastView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        viewBinding.hourlyForecastView.adapter = hourlyForecastAdapter
    }

    companion object {

        private const val CITY_NAME = "city_name"

        fun newInstance(cityName: String): Fragment {
            return WeatherDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(CITY_NAME, cityName)
                }
            }
        }
    }
}
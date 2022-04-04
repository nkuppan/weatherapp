package com.nkuppan.weatherapp.presentation.weatherdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nkuppan.weatherapp.core.extention.autoCleared
import com.nkuppan.weatherapp.core.ui.fragment.BaseFragment
import com.nkuppan.weatherapp.databinding.FragmentForecastDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherDetailFragment : BaseFragment() {

    private var viewBinding: FragmentForecastDetailsBinding by autoCleared()

    private var weatherForecastAdapter: WeatherForecastAdapter by autoCleared()

    private val viewModel: WeatherDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRefreshView()

        initializeDailyForecastView()

        initializeObservers()
    }

    private fun initializeRefreshView() {
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchWeatherInfo()
        }

        viewBinding.place.setOnClickListener {
            findNavController().navigate(
                WeatherDetailFragmentDirections.actionWeatherDetailFragmentToPlaceSearchFragment()
            )
        }

        viewBinding.settings.setOnClickListener {
            findNavController().navigate(
                WeatherDetailFragmentDirections.actionWeatherDetailFragmentToSettingsFragment()
            )
        }
    }

    private fun initializeObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.allWeatherInfo.collectLatest {
                        weatherForecastAdapter.submitList(it)
                    }
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            viewBinding.swipeRefreshLayout.isRefreshing = it
        }

        viewModel.fetchWeatherInfo()
    }

    private fun initializeDailyForecastView() {
        weatherForecastAdapter = WeatherForecastAdapter { type, model ->
            if (type == 0 && model?.alert?.isNotEmpty() == true) {
                findNavController().navigate(
                    WeatherDetailFragmentDirections.actionWeatherDetailFragmentToAlertFragment(
                        model.alert.toTypedArray()
                    )
                )
            }
        }
        viewBinding.weatherDataRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewBinding.weatherDataRecyclerView.setHasFixedSize(true)
        viewBinding.weatherDataRecyclerView.adapter = weatherForecastAdapter
    }
}
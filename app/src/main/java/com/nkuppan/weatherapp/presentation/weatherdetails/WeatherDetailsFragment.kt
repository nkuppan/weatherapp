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
import com.nkuppan.weatherapp.core.extention.showSnackBarMessage
import com.nkuppan.weatherapp.core.ui.fragment.BaseBindingFragment
import com.nkuppan.weatherapp.databinding.FragmentForecastDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherDetailFragment : BaseBindingFragment<FragmentForecastDetailsBinding>() {

    private lateinit var weatherForecastAdapter: WeatherForecastAdapter

    private val viewModel: WeatherDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRefreshView()

        initializeDailyForecastView()

        initializeObservers()
    }

    private fun initializeRefreshView() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchWeatherInfo()
        }

        binding.place.setOnClickListener {
            findNavController().navigate(
                WeatherDetailFragmentDirections.actionWeatherDetailFragmentToPlaceSearchFragment()
            )
        }

        binding.settings.setOnClickListener {
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
                launch {
                    viewModel.errorMessage.collectLatest {
                        binding.root.showSnackBarMessage(it.asString(requireContext()))
                    }
                }
            }
        }

        viewModel.fetchWeatherInfo()
    }

    private fun initializeDailyForecastView() {
        weatherForecastAdapter = WeatherForecastAdapter { type, model ->
            viewLifecycleOwner.lifecycleScope.launch {
                if (type == 0 && model?.alert?.isNotEmpty() == true) {
                    findNavController().navigate(
                        WeatherDetailFragmentDirections.actionWeatherDetailFragmentToAlertFragment(
                            model.alert.toTypedArray()
                        )
                    )
                }
            }
        }
        binding.weatherDataRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.weatherDataRecyclerView.setHasFixedSize(true)
        binding.weatherDataRecyclerView.adapter = weatherForecastAdapter
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentForecastDetailsBinding {
        return FragmentForecastDetailsBinding.inflate(inflater, container, false)
    }

    override fun bindData(binding: FragmentForecastDetailsBinding) {
        super.bindData(binding)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
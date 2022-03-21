package com.nkuppan.weatherapp.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButtonToggleGroup
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.extention.autoCleared
import com.nkuppan.weatherapp.core.ui.fragment.BaseFragment
import com.nkuppan.weatherapp.databinding.FragmentSettingBinding
import com.nkuppan.weatherapp.domain.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment(), MaterialButtonToggleGroup.OnButtonCheckedListener {

    private var binding: FragmentSettingBinding by autoCleared()

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setupViewModel()

        initializeListeners()
    }

    override fun onDestroyView() {
        unInitializeListeners()
        super.onDestroyView()
    }

    private fun initializeListeners() {
        binding.temperatureContainer.addOnButtonCheckedListener(this)
        binding.windSpeedContainer.addOnButtonCheckedListener(this)
        binding.pressureContainer.addOnButtonCheckedListener(this)
        binding.precipitationContainer.addOnButtonCheckedListener(this)
        binding.distanceContainer.addOnButtonCheckedListener(this)
        binding.timeFormatContainer.addOnButtonCheckedListener(this)
    }

    private fun unInitializeListeners() {
        binding.temperatureContainer.removeOnButtonCheckedListener(this)
        binding.windSpeedContainer.removeOnButtonCheckedListener(this)
        binding.pressureContainer.removeOnButtonCheckedListener(this)
        binding.precipitationContainer.removeOnButtonCheckedListener(this)
        binding.distanceContainer.removeOnButtonCheckedListener(this)
        binding.timeFormatContainer.removeOnButtonCheckedListener(this)
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.temperature.collectLatest {
                        binding.celsius.isChecked = it == Temperature.CELSIUS
                        binding.fahrenheit.isChecked = it == Temperature.FAHRENHEIT
                    }
                }
                launch {
                    viewModel.windSpeed.collectLatest {
                        binding.metersPerSecond.isChecked = it == WindSpeed.METERS_PER_SECOND
                        binding.kilometersPerHour.isChecked = it == WindSpeed.KILOMETERS_PER_HOUR
                        binding.milesPerHour.isChecked = it == WindSpeed.MILES_PER_HOUR
                    }
                }
                launch {
                    viewModel.pressure.collectLatest {
                        binding.hpa.isChecked = it == Pressure.HECTOPASCAL
                        binding.inhg.isChecked = it == Pressure.INCH_OF_MERCURY
                    }
                }
                launch {
                    viewModel.precipitation.collectLatest {
                        binding.millimeter.isChecked = it == Precipitation.MILLIMETER
                        binding.inches.isChecked = it == Precipitation.INCHES
                    }
                }
                launch {
                    viewModel.distance.collectLatest {
                        binding.miles.isChecked = it == Distance.MILES
                        binding.kilometers.isChecked = it == Distance.KILOMETERS
                    }
                }
                launch {
                    viewModel.timeFormat.collectLatest {
                        binding.twentyFourHourFormat.isChecked = it == TimeFormat.TWENTY_FOUR_HOUR
                        binding.twelveHourFormat.isChecked = it == TimeFormat.TWELVE_HOUR
                    }
                }
            }
        }
    }

    override fun onButtonChecked(
        group: MaterialButtonToggleGroup?,
        checkedId: Int,
        isChecked: Boolean
    ) {
        group?.let {
            when (it.id) {
                R.id.temperature_container -> {
                    viewModel.saveTemperature(
                        when (checkedId) {
                            R.id.celsius -> Temperature.CELSIUS
                            else -> Temperature.FAHRENHEIT
                        }
                    )
                }
                R.id.wind_speed_container -> {
                    viewModel.saveWindSpeed(
                        when (checkedId) {
                            R.id.meters_per_second -> {
                                WindSpeed.METERS_PER_SECOND
                            }
                            R.id.kilometers_per_hour -> {
                                WindSpeed.KILOMETERS_PER_HOUR
                            }
                            else -> {
                                WindSpeed.MILES_PER_HOUR
                            }
                        }
                    )
                }
                R.id.pressure_container -> {
                    viewModel.savePressure(
                        when (checkedId) {
                            R.id.hpa -> Pressure.HECTOPASCAL
                            else -> Pressure.INCH_OF_MERCURY
                        }
                    )
                }
                R.id.precipitation_container -> {
                    viewModel.savePrecipitation(
                        when (checkedId) {
                            R.id.millimeter -> Precipitation.MILLIMETER
                            else -> Precipitation.INCHES
                        }
                    )
                }
                R.id.distance_container -> {
                    viewModel.saveDistance(
                        when (checkedId) {
                            R.id.miles -> Distance.MILES
                            else -> Distance.KILOMETERS
                        }
                    )
                }
                R.id.time_format_container -> {
                    viewModel.saveTimeFormat(
                        when (checkedId) {
                            R.id.twenty_four_hour_format -> TimeFormat.TWENTY_FOUR_HOUR
                            else -> TimeFormat.TWELVE_HOUR
                        }
                    )
                }
            }
        }
    }
}
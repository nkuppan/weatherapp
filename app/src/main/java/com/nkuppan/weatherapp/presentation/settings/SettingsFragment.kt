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
import com.nkuppan.weatherapp.core.ui.fragment.BaseBindingFragment
import com.nkuppan.weatherapp.databinding.FragmentSettingBinding
import com.nkuppan.weatherapp.domain.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseBindingFragment<FragmentSettingBinding>(),
    MaterialButtonToggleGroup.OnButtonCheckedListener {

    private val viewModel: SettingsViewModel by viewModels()

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
        binding.themeContainer.addOnButtonCheckedListener(this)
    }

    private fun unInitializeListeners() {
        binding.temperatureContainer.removeOnButtonCheckedListener(this)
        binding.windSpeedContainer.removeOnButtonCheckedListener(this)
        binding.pressureContainer.removeOnButtonCheckedListener(this)
        binding.precipitationContainer.removeOnButtonCheckedListener(this)
        binding.distanceContainer.removeOnButtonCheckedListener(this)
        binding.timeFormatContainer.removeOnButtonCheckedListener(this)
        binding.themeContainer.removeOnButtonCheckedListener(this)
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.temperature.collectLatest {
                        if (it == Temperature.CELSIUS) {
                            binding.celsius.isChecked = true
                        } else if (it == Temperature.FAHRENHEIT) {
                            binding.fahrenheit.isChecked = true
                        }
                    }
                }
                launch {
                    viewModel.windSpeed.collectLatest {
                        when (it) {
                            WindSpeed.METERS_PER_SECOND -> {
                                binding.metersPerSecond.isChecked = true
                            }
                            WindSpeed.KILOMETERS_PER_HOUR -> {
                                binding.kilometersPerHour.isChecked = true
                            }
                            WindSpeed.MILES_PER_HOUR -> {
                                binding.milesPerHour.isChecked = true
                            }
                        }
                    }
                }
                launch {
                    viewModel.pressure.collectLatest {
                        if (it == Pressure.HECTOPASCAL) {
                            binding.hpa.isChecked = true
                        } else if (it == Pressure.INCH_OF_MERCURY) {
                            binding.inhg.isChecked = true
                        }
                    }
                }
                launch {
                    viewModel.precipitation.collectLatest {
                        if (it == Precipitation.MILLIMETER) {
                            binding.millimeter.isChecked = true
                        } else if (it == Precipitation.INCHES) {
                            binding.inches.isChecked = true
                        }
                    }
                }
                launch {
                    viewModel.distance.collectLatest {
                        if (it == Distance.MILES) {
                            binding.miles.isChecked = true
                        } else if (it == Distance.KILOMETERS) {
                            binding.kilometers.isChecked = true
                        }
                    }
                }
                launch {
                    viewModel.timeFormat.collectLatest {
                        if (it == TimeFormat.TWENTY_FOUR_HOUR) {
                            binding.twentyFourHourFormat.isChecked = true
                        } else if (it == TimeFormat.TWELVE_HOUR) {
                            binding.twelveHourFormat.isChecked = true
                        }
                    }
                }
                launch {
                    viewModel.theme.collectLatest {
                        if (it == Theme.LIGHT_THEME) {
                            binding.lightTheme.isChecked = true
                        } else if (it == Theme.DARK_THEME) {
                            binding.darkTheme.isChecked = true
                        }
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
        if (!isChecked) {
            return
        }

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
                R.id.theme_container -> {
                    viewModel.saveTheme(
                        when (checkedId) {
                            R.id.light_theme -> Theme.LIGHT_THEME
                            else -> Theme.DARK_THEME
                        }
                    )
                }
            }
        }
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun bindData(binding: FragmentSettingBinding) {
        super.bindData(binding)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
package com.nkuppan.weatherapp.presentation.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.common.truth.Truth
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class SettingsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testLaunchSettingsFragmentInHiltContainer() {
        launchFragmentInHiltContainer<SettingsFragment> {
            Truth.assertThat(this).isNotNull()
        }
    }

    @Test
    fun testTemperatureInitialState() {
        launchFragmentInHiltContainer<SettingsFragment> {
            Truth.assertThat(this).isNotNull()

            (this as SettingsFragment).view?.let { view ->
                val group = view.findViewById<MaterialButtonToggleGroup>(R.id.temperature_container)
                Truth.assertThat(group.checkedButtonId).isEqualTo(R.id.celsius)
            }
        }
    }

    @Test
    fun testWindSpeedInitialState() {
        launchFragmentInHiltContainer<SettingsFragment> {
            Truth.assertThat(this).isNotNull()

            (this as SettingsFragment).view?.let { view ->
                val group = view.findViewById<MaterialButtonToggleGroup>(R.id.wind_speed_container)
                Truth.assertThat(group.checkedButtonId).isEqualTo(R.id.meters_per_second)
            }
        }
    }

    @Test
    fun testPressureInitialState() {
        launchFragmentInHiltContainer<SettingsFragment> {
            Truth.assertThat(this).isNotNull()

            (this as SettingsFragment).view?.let { view ->
                val group = view.findViewById<MaterialButtonToggleGroup>(R.id.pressure_container)
                Truth.assertThat(group.checkedButtonId).isEqualTo(R.id.hpa)
            }
        }
    }

    @Test
    fun testPrecipitationInitialState() {
        launchFragmentInHiltContainer<SettingsFragment> {
            Truth.assertThat(this).isNotNull()

            (this as SettingsFragment).view?.let { view ->
                val group = view.findViewById<MaterialButtonToggleGroup>(R.id.precipitation_container)
                Truth.assertThat(group.checkedButtonId).isEqualTo(R.id.millimeter)
            }
        }
    }

    @Test
    fun testDistanceInitialState() {
        launchFragmentInHiltContainer<SettingsFragment> {
            Truth.assertThat(this).isNotNull()

            (this as SettingsFragment).view?.let { view ->
                val group = view.findViewById<MaterialButtonToggleGroup>(R.id.distance_container)
                Truth.assertThat(group.checkedButtonId).isEqualTo(R.id.kilometers)
            }
        }
    }

    @Test
    fun testTimeFormatInitialState() {
        launchFragmentInHiltContainer<SettingsFragment> {
            Truth.assertThat(this).isNotNull()

            (this as SettingsFragment).view?.let { view ->
                val group = view.findViewById<MaterialButtonToggleGroup>(R.id.time_format_container)
                Truth.assertThat(group.checkedButtonId).isEqualTo(R.id.twenty_four_hour_format)
            }
        }
    }

    @Test
    fun testThemeInitialState() {
        launchFragmentInHiltContainer<SettingsFragment> {
            Truth.assertThat(this).isNotNull()

            (this as SettingsFragment).view?.let { view ->
                val group = view.findViewById<MaterialButtonToggleGroup>(R.id.theme_container)
                Truth.assertThat(group.checkedButtonId).isEqualTo(R.id.light_theme)
            }
        }
    }
}
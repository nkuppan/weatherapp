package com.nkuppan.weatherapp.presentation.alert

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.filters.MediumTest
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
class AlertFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testLaunchAlertListFragmentInHiltContainer() {
        launchFragmentInHiltContainer<AlertFragment>(commonAlertInput) {
            Truth.assertThat(this).isNotNull()
        }
    }

    @Test
    fun testLaunchAlertListAndCheckTitle() {
        launchFragmentInHiltContainer<AlertFragment>(commonAlertInput) {
            Truth.assertThat(this).isNotNull()
            Truth.assertThat(this.requireActivity()).isNotNull()

            val alertFragment = this as AlertFragment

            val toolbar = alertFragment.view?.findViewById<Toolbar>(R.id.toolbar)
            Truth.assertThat(toolbar?.title).isEqualTo(getString(R.string.government_alerts))

            val recyclerView = alertFragment.view?.findViewById<RecyclerView>(R.id.recyclerView)
            Truth.assertThat(recyclerView?.adapter?.itemCount).isEqualTo(2)
        }
    }

    companion object {
        private val commonAlertInput = Bundle().apply {
            putParcelableArray(
                "alerts", arrayOf(
                    AlertUIModel("alert", "", "", ""),
                    AlertUIModel("alert", "", "", "")
                )
            )
        }
    }
}
package com.nkuppan.weatherapp.utils

import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

open class BaseTest {

    private lateinit var autoCloseable: AutoCloseable

    @Before
    open fun onCreate() {
        autoCloseable = MockitoAnnotations.openMocks(this)
    }

    @After
    open fun onDestroy() {
        autoCloseable.close()
    }
}
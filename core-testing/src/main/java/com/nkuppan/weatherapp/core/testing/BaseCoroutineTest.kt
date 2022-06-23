package com.nkuppan.weatherapp.core.testing

import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

open class BaseCoroutineTest {

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
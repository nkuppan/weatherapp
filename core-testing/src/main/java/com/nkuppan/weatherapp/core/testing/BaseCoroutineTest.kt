package com.nkuppan.weatherapp.core.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
open class BaseCoroutineTest {

    protected val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var autoCloseable: AutoCloseable

    @Before
    open fun onCreate() {
        Dispatchers.setMain(testCoroutineDispatcher)
        autoCloseable = MockitoAnnotations.openMocks(this)
    }

    @After
    open fun onDestroy() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        autoCloseable.close()
    }
}
package com.nkuppan.weatherapp.buildsrc

object Versions {
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 31

    const val versionCode = 1
    const val versionName = "1.0.0"

    const val androidGradlePlugin = "7.2.0"
    const val ktlint = "0.42.1"
    const val glide = "4.12.0"

    object Kotlin {
        const val kotlin = "1.7.0"
        const val coroutines = "1.6.3"
    }

    object AndroidX {
        const val core = "1.8.0"
        const val coreTest = "2.1.0"
        const val activity = "1.4.0"
        const val fragment = "1.4.1"
        const val appCompat = "1.4.2"
        const val constraintLayout = "2.1.0-beta02"
        const val recyclerView = "1.2.1"
        const val swipeRefreshLayout = "1.2.0-alpha01"
        const val lifecycle = "2.4.0-alpha02"
        const val room = "2.4.2"
        const val paging = "3.1.1"
        const val navigation = "2.4.2"
        const val dataStore = "1.0.0"

        object Test {
            const val core = "1.4.0"
            const val runner = "1.4.0"
            const val rules = "1.4.0"
            const val jUnit = "1.1.3"
            const val jUnitCore = "4.13.2"
            const val espresso = "3.4.0"
        }
    }

    object Google {
        const val truth = "1.1.3"
        const val material = "1.6.0"
        const val hilt = "2.42"
    }

    object Square {
        const val okHttp = "4.9.1"
        const val retrofit = "2.9.0"
    }

    object Test {
        const val jUnitCore = "4.13.2"
        const val cashTurbine = "0.7.0"
    }

    object Mockito {
        const val core = "3.3.3"
        const val ktx = "4.0.0"
        const val android = "4.3.1"
    }

    object Robolectric {
        const val core = "4.6"
    }

    object Jacoco {
        const val gradle = "0.8.7"
    }
}
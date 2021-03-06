import com.nkuppan.weatherapp.buildsrc.Libs
import com.nkuppan.weatherapp.buildsrc.Versions

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        testInstrumentationRunner = Libs.AndroidX.Test.instrumentationRunner
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    api(Libs.Kotlin.stdlib)
    api(Libs.Kotlin.Coroutines.core)
    api(Libs.Kotlin.Coroutines.android)

    api(Libs.AndroidX.Core.ktx)
    api(Libs.AndroidX.appCompat)
    api(Libs.AndroidX.constraintLayout)
    api(Libs.AndroidX.recyclerView)
    api(Libs.AndroidX.swipeRefreshLayout)

    api(Libs.AndroidX.Activity.ktx)
    api(Libs.AndroidX.Fragment.ktx)

    api(Libs.AndroidX.Lifecycle.liveDataKtx)
    api(Libs.AndroidX.Lifecycle.viewModelKtx)
    api(Libs.AndroidX.Lifecycle.runtime)

    api(Libs.AndroidX.Navigation.fragmentKtx)
    api(Libs.AndroidX.Navigation.uiKtx)

    api(Libs.Google.material)

    api(Libs.AndroidX.DataStore.preferences)

    implementation(Libs.Glide.core)
    kapt(Libs.Glide.compiler)
}
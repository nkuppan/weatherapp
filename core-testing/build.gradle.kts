import com.nkuppan.weatherapp.buildsrc.Libs
import com.nkuppan.weatherapp.buildsrc.Versions

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources {
            excludes.add("**/attach_hotspot_windows.dll")
            excludes.add("META-INF/licenses/**")
            excludes.add("META-INF/AL2.0")
            excludes.add("META-INF/LGPL2.1")
        }
    }
}

dependencies {

    implementation(Libs.Google.Hilt.android)
    kapt(Libs.Google.Hilt.hiltCompiler)

    debugApi(Libs.AndroidX.Fragment.test)

    api(Libs.Google.truth)
    api(Libs.Mockito.core)
    api(Libs.Mockito.ktx)
    api(Libs.Kotlin.Coroutines.test)
    api(Libs.Google.Hilt.test)

    api(Libs.AndroidX.Core.test)
    api(Libs.AndroidX.Test.JUnit.ktx)
    api(Libs.AndroidX.Test.Espresso.core)

    api(Libs.AndroidX.Test.runner)
    api(Libs.AndroidX.Test.JUnit.core)
    api(Libs.Square.mockWebServer)
}
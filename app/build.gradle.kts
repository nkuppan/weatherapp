import com.nkuppan.weatherapp.buildsrc.Libs
import com.nkuppan.weatherapp.buildsrc.Versions

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {

        applicationId = "com.nkuppan.weatherapp"

        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        versionCode = Versions.versionCode
        versionName = Versions.versionName

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = "com.nkuppan.weatherapp.utils.HiltTestRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
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

    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Libs.AndroidX.Room.runtime)
    kapt(Libs.AndroidX.Room.compiler)
    implementation(Libs.AndroidX.Room.ktx)

    implementation(Libs.Square.okHttp)
    implementation(Libs.Square.Retrofit.retrofit)
    implementation(Libs.Square.Retrofit.gsonConverter)
    implementation(Libs.Square.Retrofit.logging)

    implementation(Libs.Glide.core)
    kapt(Libs.Glide.compiler)

    implementation(Libs.Google.Hilt.android)
    kapt(Libs.Google.Hilt.hiltCompiler)

    /**
     * Android Testing Related Library
     */
    //Core testing source module it will hold the
    kaptAndroidTest(Libs.Google.Hilt.hiltCompiler)
    androidTestImplementation(project(":core-testing"))
    testImplementation(project(":core-testing"))
}
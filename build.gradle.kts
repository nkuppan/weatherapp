import com.nkuppan.weatherapp.buildsrc.Libs

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    }
}
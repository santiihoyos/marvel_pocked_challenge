buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        dependencies {
            classpath(libs.classpath.kotlin)
            classpath(libs.classpath.gradle)
        }
    }
}

plugins {
    //Violation scope it's a open issue into Versions catalog repo...
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
}
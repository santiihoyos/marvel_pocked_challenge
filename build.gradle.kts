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
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.library) apply false
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.application) apply false
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlinAndroid) apply false
}
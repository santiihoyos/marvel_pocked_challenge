plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {

    compileSdk  = 32

    defaultConfig {
        applicationId = "com.santiihoyos.marvelpocket"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "API_BASE_URL", "\"https://gateway.marvel.com\"")
            buildConfigField("String", "API_PUB_KEY", "\"d1c4c7d44145047fd4f399ba009c62fa\"")
            buildConfigField("String", "API_PRIV_KEY", "\"059daac047ad259a2c2d87f4011e3ddea540be99\"")
        }
        getByName("release") {
            // isMinifyEnabled = true
            buildConfigField("String", "API_BASE_URL", "\"https://gateway.marvel.com\"")
            buildConfigField("String", "API_PUB_KEY", "\"d1c4c7d44145047fd4f399ba009c62fa\"")
            buildConfigField("String", "API_PRIV_KEY", "\"059daac047ad259a2c2d87f4011e3ddea540be99\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.asProvider().get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":characters"))
    implementation(project(":api"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifeCycle)

    implementation(libs.compose.ui)
    implementation(libs.compose.md3)
    implementation(libs.compose.toolingPreview)
    implementation(libs.compose.liveData)
    implementation(libs.compose.navigation)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    testImplementation(libs.test.jUnit)

    androidTestImplementation(libs.androidx.ext.jUnit)
    androidTestImplementation(libs.test.expresso)
    androidTestImplementation(libs.test.composeUi)

    debugImplementation(libs.test.composeUiTooling)
    debugImplementation(libs.test.composeUiManifest)
}
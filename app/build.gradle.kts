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
            buildConfigField("String", "API_PUB_KEY", "\"{YOUR_API}\"")
            buildConfigField("String", "API_PRIV_KEY", "\"YOUR_API\"")
        }
        getByName("release") {
            // isMinifyEnabled = true we need add rules to entities etc...
            buildConfigField("String", "API_BASE_URL", "\"https://gateway.marvel.com\"")
            buildConfigField("String", "API_PUB_KEY", "\"{YOUR_API}\"")
            buildConfigField("String", "API_PRIV_KEY", "\"YOUR_API\"")
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
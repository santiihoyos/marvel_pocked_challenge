plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.asProvider().get()
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":api"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifeCycle)
    implementation(libs.compose.ui)
    implementation(libs.compose.md3)
    implementation(libs.compose.toolingPreview)
    implementation(libs.compose.liveData)
    implementation(libs.compose.paging)
    implementation(libs.compose.navigation)
    implementation(libs.coil)
    implementation(libs.coilCompose)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    testImplementation(libs.test.jUnit)

    androidTestImplementation(libs.androidx.ext.jUnit)
    androidTestImplementation(libs.test.expresso)
    androidTestImplementation(libs.test.composeUi)

    debugImplementation(libs.test.composeUiTooling)
    debugImplementation(libs.test.composeUiManifest)
}
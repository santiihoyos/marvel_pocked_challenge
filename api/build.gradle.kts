plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.retrofit.adapter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.loggin)
    implementation(libs.koin.kotlin)

    testImplementation(libs.test.jUnit)
    testImplementation(libs.test.mockitoKtx)
    testImplementation(libs.test.coroutines)
}
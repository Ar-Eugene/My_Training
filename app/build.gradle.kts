plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.mytraining"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mytraining"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature-profile"))
    implementation(project(":feature-favorites"))
    implementation(project(":feature-oge"))
    implementation(project(":feature-ege"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.androidx.compose.viewmodel)
    implementation(libs.androidx.runtime.livedata)

    // Navigation for Compose
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.com.hilt)
    ksp(libs.com.hilt.ksp)
    implementation(libs.androidx.hilt.navigation.compose)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.play.services.location)

    // Accompanist для управления навигацией с BottomSheet
    implementation(libs.accompanist.navigation.material)
    implementation(libs.accompanist.systemuicontroller)


    //Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.converter.gson)
    implementation (libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    //Datastore
    implementation(libs.androidx.datastore.preferences)
}
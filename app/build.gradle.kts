plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("kotlin-kapt") // ✅ Required for Room annotation processing
}

android {
    namespace = "com.example.quoteofthedayapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.quoteofthedayapp"
        minSdk = 22
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    testOptions {
        unitTests.all {
            it.isEnabled = false
        }
    }
}

dependencies {
    // ✅ Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))

    // ✅ Compose Core
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3:1.2.1")

    // ✅ Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // ✅ Lifecycle + ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // ✅ Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // ✅ Coil for images
    implementation("io.coil-kt:coil-compose:2.5.0")

    // ✅ Activity Compose
    implementation("androidx.activity:activity-compose:1.9.0")

    // ✅ DataStore for persistent profile storage
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // ✅ Room dependencies for favorite quotes persistence
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // ✅ Debug tools
    debugImplementation("androidx.compose.ui:ui-tooling")

    // ✅ Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "2.0.0"
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.kalimani"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kalimani"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //LiveData
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.1")
    // splash-screen
    implementation("androidx.core:core-splashscreen:1.0.1")
    // extended icon
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    //navigation
    implementation("androidx.navigation:navigation-compose:2.8.0-rc01")
    //serlization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    // hilt di
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    // firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")
    implementation ("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    implementation ("com.google.firebase:firebase-storage-ktx:21.0.1")
    implementation ("com.google.android.gms:play-services-auth:21.3.0")
    implementation ("androidx.credentials:credentials:1.3.0")
    implementation ("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    //Material3
    implementation ("androidx.compose.material3:material3:1.2.1")
    //custem bottom nev bar
    implementation ("androidx.compose:compose-bom:2024.01.00")
    //ExoPlayer
    implementation ("androidx.media3:media3-exoplayer:1.3.1")  // Core ExoPlayer
    implementation ("androidx.media3:media3-ui:1.3.1")
    //preferance datastore
    implementation ("androidx.datastore:datastore-preferences:1.1.1")



}
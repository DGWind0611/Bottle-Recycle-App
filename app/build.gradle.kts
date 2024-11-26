plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.map.secret)
    id("com.google.gms.google-services") version "4.4.2"

}

android {
    namespace = "com.fcu.android.bottlerecycleapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fcu.android.bottlerecycleapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    // qr code
    implementation(libs.qr.generator)
    // google maps
    implementation(libs.google.maps)
    implementation(libs.google.maps.location)
    // glide
    implementation(libs.glide)
    implementation(libs.activity)
    implementation(libs.volley)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.jbcrypt)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

}
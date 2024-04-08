plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("ru.practicum.android.diploma.plugins.developproperties")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
}

android {
    namespace = "ru.practicum.android.diploma"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.practicum.android.diploma"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", name = "HH_ACCESS_TOKEN", value = "\"${developProperties.hhAccessToken}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)

    // UI layer libraries
    implementation(libs.ui.material)
    implementation(libs.ui.constraintLayout)

    // region Unit tests
    testImplementation(libs.unitTests.junit)
    // endregion

    // region UI tests
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)
    // endregion

    // Coroutines
    implementation(libs.coroutines)
    // endregion

    // Koin
    implementation(libs.koin)
    // endregion

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converterGson)
    // endregion

    // Gson
    implementation(libs.gson)
    // endregion

    // Fragments, NavigationGraph and JetpackNavigation
    implementation(libs.fragments.navigationFragment)
    implementation(libs.fragments.navigationUi)
    implementation(libs.fragments.fragmentKtx)
    // endregion

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    // endregion

    // ViewModel and LiveData
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    // endregion

    // ViewPager
    implementation(libs.viewPager)
    // endregion

    // Bindings
    implementation(libs.viewBinding)
    // endregion

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    // endregion

}

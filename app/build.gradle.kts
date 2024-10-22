import java.io.FileInputStream
import java.util.Properties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    //id("dagger.hilt.android.plugin")
}

fun getEnvProperty(propertyName: String): String? {
    val properties = Properties()

    // Load properties from local.properties if the file exists
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.isFile) {
        properties.load(FileInputStream(localPropertiesFile))
    }

    // Check for the property in local.properties or environment variables
    var property: String? = properties.getProperty(propertyName) ?: System.getenv(propertyName)

    // Override with Gradle project property if it's available
    if (project.hasProperty(propertyName)) {
        property = project.property(propertyName) as String
    }

    return property
}
val apiKey: String =getEnvProperty("API_KEY") ?: ""

android {
    namespace = "com.example.mvi_clean_room_hilt_flows"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mvi_clean_room_hilt_flows"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")

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
        buildConfig = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //jetpack libs
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //Retorfit & okhttps
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //implementation(libs.androidx.material)

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.40.5")
    kapt(libs.hilt.android.compiler)
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
}

import com.trex.laxmiemi.utils.UploadDebugReleaseApkTask
import com.trex.laxmiemi.utils.UploadReleaseApkTask
import java.io.FileInputStream
import java.util.Properties


val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

// Register the task
tasks.register<UploadReleaseApkTask>("uploadReleaseApk") {
    description = "Uploads the release APK to remote server"
    group = "upload"
    dependsOn("assembleRelease")
}
// Register the task
tasks.register<UploadDebugReleaseApkTask>("uploadDebugReleaseApk") {
    description = "Uploads the release APK to remote server"
    group = "upload"
    dependsOn("assembleRelease")
}

android {
    namespace = "com.trex.laxmiemi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.trex.laxmiemi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Add this to read password from file
    fun readPasswordFromFile(filename: String): String =
        try {
            File(filename).readText().trim()
        } catch (e: Exception) {
            throw GradleException("Failed to read password from file: $filename", e)
        }

    signingConfigs {
        create("release") {
            keyAlias = readPasswordFromFile(keystoreProperties["keyAlias"] as String)
            keyPassword = readPasswordFromFile(keystoreProperties["keyPassword"] as String)
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = readPasswordFromFile(keystoreProperties["storePassword"] as String)
        }
    }
    buildTypes {

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            val baseUrl = "https://shieldserver-00on.onrender.com"
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "CLIENT_APK_URL", "\"$baseUrl/api/apk/debug/url\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }

        release {

            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders.put("enablePlayIntegrity", "false")

            val baseUrl = "https://shieldserver-00on.onrender.com"
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "CLIENT_APK_URL", "\"$baseUrl/api/apk/url\"")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        dataBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
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
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.runtime.livedata)
    implementation(project(":rexnetwork"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.hbb20:ccp:2.7.3")
    implementation("com.google.firebase:firebase-firestore")
    implementation("io.coil-kt:coil:2.7.0")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("androidx.compose.material:material:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.compose.runtime:runtime:1.5.3")

    implementation("androidx.compose.material:material-icons-extended:1.7.3")
    implementation("com.lightspark:compose-qr-code:1.0.1")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("com.google.code.gson:gson:2.11.0")

    // ExoPlayer dependencies
    implementation("androidx.media3:media3-exoplayer:1.2.0")
    implementation("androidx.media3:media3-ui:1.2.0")
    implementation("androidx.media3:media3-common:1.2.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
}

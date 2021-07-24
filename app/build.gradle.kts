plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = Sdk.COMPILE_SDK_VERSION
    buildToolsVersion = Sdk.BUILD_TOOL_SDK_VERSION

    defaultConfig {
        applicationId = "com.example.itbook"
        minSdk = Sdk.MIN_SDK_VERSION
        targetSdk = Sdk.TARGET_SDK_VERSION
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            debuggable(true)
            isMinifyEnabled = false
        }

        getByName("release") {
            debuggable(false)
            isMinifyEnabled = true
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

    packagingOptions {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(project(":ItBookApi"))

    implementation(SupportLibs.ANDROID_MATERIAL)
    implementation(SupportLibs.ANDROIDX_CORE_KTX)
    implementation(SupportLibs.ANDROIDX_APPCOMPAT)
    implementation(SupportLibs.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(SupportLibs.ANDROIDX_LIFECYCLE_LIVEDATA)
    implementation(SupportLibs.ANDROIDX_LIFECYCLE_VIEWMODEL)
    implementation(SupportLibs.ANDROIDX_NAVIGATION_FRAGMENT_KTX)
    implementation(SupportLibs.ANDROIDX_NAVIGATION_UI_KTX)
    implementation(SupportLibs.ANDROIDX_ACTIVITY_KTX)
    implementation(SupportLibs.ANDROIDX_FRAGMENT_KTX)
    implementation(SupportLibs.ANDROIDX_FRAGMENT_KTX)

    implementation(KotlinCoroutine.ANDROID)
    implementation(KotlinCoroutine.CORE)

    implementation(Glide.GLIDE)

    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_ANDROID_COMPILER)
    kapt(SupportLibs.ANDROIDX_HILT_COMPILER)

    testImplementation(JUnit.JUNIT)
    androidTestImplementation(KotlinCoroutine.TEST)
    androidTestImplementation(SupportLibs.ANDROIDX_TEST_ESPRESSO_CORE)
    androidTestImplementation(SupportLibs.ANDROIDX_ARCH_CORE_TESTING)
    androidTestImplementation(SupportLibs.ANDROIDX_TEST_JUNIT)
}

kapt {
    correctErrorTypes = true
}
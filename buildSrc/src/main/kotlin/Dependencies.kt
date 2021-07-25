object Sdk {
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 30
    const val COMPILE_SDK_VERSION = 30
    const val BUILD_TOOL_SDK_VERSION = "30.0.3"
}

object Versions {
    const val ANDROID_MATERIAL = "1.4.0"

    const val ANDROIDX_CORE = "1.6.0"
    const val ANDROIDX_APPCOMPAT = "1.3.1"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "2.0.4"
    const val ANDROIDX_LIFECYCLE = "2.3.1"
    const val ANDROIDX_ROOM = "2.3.0"
    const val ANDROIDX_NAVIGATION = "2.3.5"
    const val ANDROIDX_ACTIVITY = "1.2.3"
    const val ANDROIDX_FRAGMENT = "1.3.6"
    const val ANDROIDX_HILT = "1.0.0"
    const val ANDROIDX_ESPRESSO = "3.4.0"
    const val ANDROID_ARCH_CORE = "2.1.0"
    const val ANDROIDX_TEST_JUNIT = "1.1.3"

    const val GLIDE = "4.12.0"
    const val RETROFIT = "2.9.0"
    const val OKHTTP3 = "4.9.1"
    const val KOTLIN_COROUTINE = "1.5.0"
    const val HILT = "2.37"

    const val JUNIT = "4.13.2"
    const val IT_BOOK_API = "1.0.0"
}

object SupportLibs {
    const val ANDROID_MATERIAL = "com.google.android.material:material:${Versions.ANDROID_MATERIAL}"

    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE}"
    const val ANDROIDX_CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.ANDROID_MATERIAL}"

    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT}"

    const val ANDROIDX_LIFECYCLE_LIVEDATA =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.ANDROIDX_LIFECYCLE}"
    const val ANDROIDX_LIFECYCLE_VIEWMODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE}"

    const val ANDROIDX_ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ANDROIDX_ROOM}"
    const val ANDROIDX_ROOM_KTX = "androidx.room:room-ktx:${Versions.ANDROIDX_ROOM}"
    const val ANDROIDX_ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ANDROIDX_ROOM}"

    const val ANDROIDX_NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${Versions.ANDROIDX_NAVIGATION}"
    const val ANDROIDX_NAVIGATION_UI_KTX =
        "androidx.navigation:navigation-ui-ktx:${Versions.ANDROIDX_NAVIGATION}"

    const val ANDROIDX_ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ANDROIDX_ACTIVITY}"

    const val ANDROIDX_FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.ANDROIDX_FRAGMENT}"

    const val ANDROIDX_HILT_COMPILER = "androidx.hilt:hilt-compiler:${Versions.ANDROIDX_HILT}"

    const val ANDROIDX_TEST_ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${Versions.ANDROIDX_ESPRESSO}"
    const val ANDROIDX_ARCH_CORE_TESTING =
        "androidx.test.espresso:espresso-core:${Versions.ANDROID_ARCH_CORE}"
    const val ANDROIDX_TEST_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_JUNIT}"
}

object SquareUp {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3}"
}

object Glide {
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
}

object KotlinCoroutine {
    const val ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN_COROUTINE}"
    const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINE}"
    const val TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLIN_COROUTINE}"
}

object Hilt {
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
}

object JUnit {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
}

object ItBookApi {
    const val IT_BOOK_API = "com.example.itbookapi:itbookapi:${Versions.IT_BOOK_API}"
}
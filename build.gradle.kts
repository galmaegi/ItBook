// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        // There is an bug in kotlin 1.5.20 to implement HILT, Error message like below
        // Expected @HiltAndroidApp to have a value. Did you forget to apply the Gradle Plugin?
        // https://github.com/google/dagger/issues/2684
        // See workaround : https://youtrack.jetbrains.com/issue/KT-47416
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.37")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
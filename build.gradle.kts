// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.5.10")
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.37")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
@file:Suppress("DSL_SCOPE_VIOLATION")

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://repository.map.naver.com/archive/maven")
    }

    dependencies {
        classpath(libs.androidx.safeargs)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.navigation) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.dynamic) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.hilt) apply false
}

apply {
    from("gradle/projectDependencyGraph.gradle")
}
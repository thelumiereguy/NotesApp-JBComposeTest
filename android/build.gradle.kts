plugins {
    id("org.jetbrains.compose") version versions.jetBrainsCompose
    id("com.android.application")
    kotlin("android")
    id("kotlinx-serialization")
}

group = "me.user"
version = "1.0"

repositories {
    google()
}

dependencies {
    implementation(project(":common"))
    api("org.hildan.krossbow:krossbow-websocket-okhttp:${versions.krossbowVersion}")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "me.user.android"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    packagingOptions {
        pickFirst("META-INF/kotlinx-io.kotlin_module")
        pickFirst("META-INF/atomicfu.kotlin_module")
        pickFirst("META-INF/kotlinx-coroutines-io.kotlin_module")
    }
}
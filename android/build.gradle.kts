plugins {
    id("org.jetbrains.compose") version versions.jetBrainsCompose
    id("com.android.application")
    kotlin("android")
}

group = "me.user"
version = "1.0"

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("io.insert-koin:koin-android:3.0.1")
    implementation("io.insert-koin:koin-androidx-compose:3.0.1")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
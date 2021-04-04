import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version versions.jetBrainsCompose
    id("com.android.library")
    id("kotlin-android-extensions")
    id("kotlinx-serialization")
}

group = "me.user"
version = "1.0"

repositories {
    google()
    mavenCentral()
}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${versions.serialization}")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.coroutines}")

                implementation("org.hildan.krossbow:krossbow-stomp-core:${versions.krossbowVersion}")
                implementation("org.hildan.krossbow:krossbow-websocket-core:${versions.krossbowVersion}")
                implementation("org.hildan.krossbow:krossbow-stomp-kxserialization:${versions.krossbowVersion}")

//                // KODE IN
                implementation("org.kodein.di:kodein-di-core:${versions.kodein}")
                implementation("org.kodein.di:kodein-di-erased:${versions.kodein}")

                // KTOR
                api("io.ktor:ktor-client-core:${versions.ktor}")
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:${versions.appCompat}")
                api("androidx.activity:activity-compose:${versions.activityCompose}")
                api("androidx.core:core-ktx:${versions.coreKtx}")

                // COROUTINE
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.coroutines}")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }
        val desktopMain by getting
        val desktopTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.coroutines}")
            }
        }
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }
}
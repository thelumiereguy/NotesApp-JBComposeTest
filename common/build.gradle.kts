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
                api("org.jetbrains.kotlin:kotlin-stdlib-common")

                api("org.jetbrains.kotlinx:kotlinx-serialization-json:${versions.serialization}")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.coroutines}")

                api("org.hildan.krossbow:krossbow-stomp-core:${versions.krossbowVersion}")
                api("org.hildan.krossbow:krossbow-websocket-core:${versions.krossbowVersion}")
                api("org.hildan.krossbow:krossbow-stomp-kxserialization:${versions.krossbowVersion}")

                // MOKO - MVVM
//                implementation("dev.icerock.moko:mvvm:${versions.moko_mvvm}")

                // KODE IN
                api("org.kodein.di:kodein-di-core:${versions.kodein}")
                api("org.kodein.di:kodein-di-erased:${versions.kodein}")

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
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }
        val desktopMain by getting
        val desktopTest by getting
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
import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version versions.kotlinVersion
    id("org.jetbrains.compose") version versions.jetBrainsCompose
    id("com.android.library")
    id("kotlin-android-extensions")
    id("com.squareup.sqldelight")
}

group = "me.user"
version = "1.0"

repositories {
    google()
    mavenCentral()
}

android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
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

//                // KOIN
                api("io.insert-koin:koin-core:${versions.koin}")
                api("io.insert-koin:koin-test:${versions.koin}")

                api("moe.tlaster:precompose:${versions.precompose}")

                // KTOR
                implementation("io.ktor:ktor-client-core:${versions.ktor}")
                implementation("io.ktor:ktor-client-json:${versions.ktor}")
                implementation("io.ktor:ktor-client-logging:${versions.ktor}")
                implementation("io.ktor:ktor-client-serialization:${versions.ktor}")

                // SqlDelight
                implementation("com.squareup.sqldelight:runtime:${versions.sqlDelight}")
                implementation("com.squareup.sqldelight:coroutines-extensions:${versions.sqlDelight}")

            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:${versions.appCompat}")
                api("androidx.activity:activity-compose:${versions.activityCompose}")
                api("androidx.core:core-ktx:${versions.coreKtx}")
                api("org.hildan.krossbow:krossbow-websocket-okhttp:${versions.krossbowVersion}")
                // COROUTINE
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.coroutines}")

                implementation("io.ktor:ktor-client-android:${versions.ktor}")

                implementation("com.squareup.sqldelight:android-driver:${versions.sqlDelight}")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.coroutines}")
                implementation("io.ktor:ktor-client-apache:${versions.ktor}")

                implementation("com.squareup.sqldelight:sqlite-driver:${versions.sqlDelight}")
                implementation("org.xerial:sqlite-jdbc:${versions.sqliteJdbcDriver}")
            }
        }
        val desktopTest by getting
    }
}

sqldelight {
    database("NotesDatabase") {
        packageName = "me.user.notes.db"
        sourceFolders = listOf("sqldelight")
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
//    resolutionStrategy {
//        eachPlugin {
//            if (requested.id.id == "kotlin-platform-common") {
//                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
//            }
//
//            if (requested.id.id == "kotlinx-serialization") {
//                //useModule("org.jetbrains.kotlinx:kotlinx-gradle-serialization-plugin:${requested.version}")
//                useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
//            }
//
//            if (requested.id.id == "kotlin-multiplatform") {
//                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
//            }
//        }
//    }
}

rootProject.name = "KMPTest2"

include(":android")
include(":desktop")
include(":common")

enableFeaturePreview("GRADLE_METADATA")
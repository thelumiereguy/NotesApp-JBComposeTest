buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlinVersion}")
        classpath("com.android.tools.build:gradle:7.0.0-alpha14")
        classpath("com.squareup.sqldelight:gradle-plugin:${versions.sqlDelight}")
    }
}

group = "me.user"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
        maven { setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

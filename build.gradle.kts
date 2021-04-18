buildscript {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", versions.kotlinVersion))
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath("com.squareup.sqldelight:gradle-plugin:${versions.sqlDelight}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${versions.kotlinVersion}")
    }
}

group = "me.user"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven { setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

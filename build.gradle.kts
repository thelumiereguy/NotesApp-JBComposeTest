buildscript {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlinVersion}")
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("com.squareup.sqldelight:gradle-plugin:${versions.sqlDelight}")
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

object versions {
    const val kotlinVersion = "1.4.32"

    const val krossbowVersion = "1.2.0"

    const val coroutines = "1.4.2"

    const val moko_mvvm = "0.9.2"

    const val serialization = "1.1.0"

    const val ktor = "1.5.2"

    const val koin = "3.0.1"

    const val jetBrainsCompose = "0.4.0-build180"

    const val appCompat = "1.2.0"

    const val activityCompose = "1.3.0-alpha05"

    const val coreKtx = "1.3.2"

    const val precompose = "0.1.3"

    const val sqlDelight = "1.4.4"
    const val sqliteJdbcDriver = "3.30.1"

    const val mockk = "1.10.6"

}

object Deps {
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions.coroutines}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.coroutines}"



    object KotlinTest {
        val common = "org.jetbrains.kotlin:kotlin-test-common:${versions.kotlinVersion}"
        val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${versions.kotlinVersion}"
        val jvm = "org.jetbrains.kotlin:kotlin-test:${versions.kotlinVersion}"
        val junit = "org.jetbrains.kotlin:kotlin-test-junit:${versions.kotlinVersion}"
    }
}
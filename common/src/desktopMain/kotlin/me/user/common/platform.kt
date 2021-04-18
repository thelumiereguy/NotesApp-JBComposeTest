package me.user.common

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.runBlocking
import me.user.notes.db.NotesDatabase
import org.hildan.krossbow.stomp.StompClient
import org.koin.dsl.module

actual fun getPlatformName(): String {
    return "Desktop"
}

internal actual fun platformModule() = module {
    single {
        val driver =
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also { (NotesDatabase.Schema.create(it)) }
        NotesDatabase(driver) ?: null
    }

    single {
        StompClient()
    }
}

actual fun runTest(block: suspend () -> Unit) = runBlocking { block() }
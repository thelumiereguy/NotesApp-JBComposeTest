package me.user.common

import com.squareup.sqldelight.android.AndroidSqliteDriver
import me.user.notes.db.NotesDatabase
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import org.koin.dsl.module

actual fun getPlatformName(): String {
    return "Android"
}

internal actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(NotesDatabase.Schema, get(), "notes.db")
        NotesDatabase(driver)
    }

    single {
        StompClient(OkHttpWebSocketClient())
    }
}
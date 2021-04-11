package me.user.common

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import me.user.common.di.IDependencyProvider
import me.user.notes.db.NotesDatabase
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient

actual fun getPlatformName(): String {
    return "Android"
}

actual fun getStompClient(): StompClient {
    return StompClient(OkHttpWebSocketClient())
}

internal actual fun getDbClient(dependencies: IDependencyProvider): NotesDatabase? {
    if(dependencies is AndroidDependencies){
        val driver = AndroidSqliteDriver(NotesDatabase.Schema, dependencies.context, "notes.db")
        return NotesDatabase(driver)
    }
    return null
}

class AndroidDependencies(val context: Context) : IDependencyProvider
package me.user.common

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import me.user.notes.db.NotesDatabase
import org.hildan.krossbow.stomp.StompClient

actual fun getPlatformName(): String {
    return "Desktop"
}

actual fun getStompClient(): StompClient {
    return StompClient()
}

actual fun getDbClient(dependencies: IDbDependencies): NotesDatabase? {
    val driver =
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also { (NotesDatabase.Schema.create(it)) }
    return NotesDatabase(driver) ?: null
}
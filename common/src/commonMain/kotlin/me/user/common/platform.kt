package me.user.common

import me.user.notes.db.NotesDatabase
import org.hildan.krossbow.stomp.StompClient

expect fun getPlatformName(): String

expect fun getStompClient(): StompClient

expect fun getDbClient(dependencies: IDbDependencies): NotesDatabase?

interface IDbDependencies
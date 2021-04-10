package me.user.common

import me.user.common.di.IDependencyProvider
import me.user.notes.db.NotesDatabase
import org.hildan.krossbow.stomp.StompClient

expect fun getPlatformName(): String

expect fun getStompClient(): StompClient

internal expect fun getDbClient(dependencies: IDependencyProvider): NotesDatabase?
package me.user.common.feature.notes.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.user.common.feature.notes.data.models.Note
import me.user.common.feature.notes.data.network.NotesAPI
import me.user.common.feature.notes.data.network.model.NotesUpdateEventResponse
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.conversions.kxserialization.subscribe
import org.hildan.krossbow.stomp.conversions.kxserialization.withJsonConversions
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext

class NotesRepository(private val notesAPI: NotesAPI, private val client: StompClient) {

    suspend fun getAllNotes(): List<Note> {
        val notes = notesAPI.getAllNotes()
        return notes.data.notes.map {
            val (title, content, createdBy, createdOn, id) = it
            Note(
                title,
                content,
                createdBy,
                createdOn,
                formatDate(createdOn),
                id
            )
        }
    }

    private fun formatDate(date: Long): String {
        return SimpleDateFormat("dd MMMM", Locale.getDefault()).format(date)
    }

    suspend fun observeChanges(onUpdate:suspend () -> Unit) {
        with(CoroutineScope(coroutineContext)) {
            launch {
                val session = client.connect("ws://192.168.0.107:8080/ws")
                    .withJsonConversions()


                val subscription: Flow<NotesUpdateEventResponse> = session.subscribe(
                    "/topic.notes",
                    NotesUpdateEventResponse.serializer()
                )

                val collectorJob = launch {
                    subscription.collect { noteUpdateEvent ->
                        println("MainViewModel Received: ${noteUpdateEvent.toString()}")
                        onUpdate()
                    }
                }
            }
        }
    }
}
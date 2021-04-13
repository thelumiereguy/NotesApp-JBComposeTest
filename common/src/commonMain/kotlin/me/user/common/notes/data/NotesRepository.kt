package me.user.common.notes.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.user.common.notes.data.mapper.NoteMapper
import me.user.common.notes.data.models.Note
import me.user.common.notes.data.network.IP
import me.user.common.notes.data.network.NotesAPI
import me.user.common.notes.data.network.model.NoteDTO
import me.user.common.notes.data.network.model.NotesUpdateEventResponse
import me.user.notes.db.NotesDatabase
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.conversions.kxserialization.subscribe
import org.hildan.krossbow.stomp.conversions.kxserialization.withJsonConversions
import java.util.*
import kotlin.coroutines.coroutineContext

class NotesRepository(
    private val notesAPI: NotesAPI,
    private val client: StompClient,
    private val notesDatabase: NotesDatabase?,
    private val noteMapper: NoteMapper
) {

    private val notesQueries = notesDatabase?.notesQueries

    suspend fun getAllNotes(): List<Note> {
        val notes = notesAPI.getAllNotes()
        return notes.data.notes.map {
            noteMapper.toDomainEntity(it)
        }
    }

    suspend fun observeChanges(onUpdate: suspend () -> Unit) {
        with(CoroutineScope(coroutineContext)) {
            launch {
                val session = client.connect("ws://$IP/ws")
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

    suspend fun createNote(note: Note): Note {
        val noteRequestModel = noteMapper.fromDomainEntity(note)
        val noteResponse = notesAPI.createNote(noteRequestModel)
        return noteMapper.toDomainEntity(noteResponse)
    }
}
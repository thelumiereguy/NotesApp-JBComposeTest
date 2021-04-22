package me.user.common.notes.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.user.common.notes.data.mapper.NoteMapper
import me.user.common.notes.data.models.Note
import me.user.common.notes.data.network.NotesAPI
import me.user.common.notes.data.network.domain
import me.user.common.notes.data.network.model.NotesUpdateEventResponse
import me.user.common.notes.data.network.model.UpdateType
import me.user.notes.db.NotesDatabase
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.conversions.kxserialization.subscribe
import org.hildan.krossbow.stomp.conversions.kxserialization.withJsonConversions
import java.util.*
import kotlin.coroutines.coroutineContext

class NotesRepository(
    private val notesAPI: NotesAPI,
    private val client: StompClient,
    notesDatabase: NotesDatabase?,
    private val noteMapper: NoteMapper
) {

    private val notesQueries = notesDatabase?.notesQueries

    suspend fun getAllNotes() {
        val notes = notesAPI.getAllNotes()
        notes.data.notes.forEach {
            with(it) {
                notesQueries?.insertItem(id, title, content, created_by, created_on)
            }
        }
    }

    fun getAllNotesAsFlow(): Flow<List<Note>>? {
        return notesQueries?.selectAll(mapper = { id, title, content, created_by, created_on ->
            Note(title, content, created_by, created_on, id)
        })?.asFlow()?.flowOn(Dispatchers.Default)?.mapToList()
    }

    suspend fun observeChanges() {
        with(CoroutineScope(coroutineContext)) {
            launch {
                val session = client.connect("ws://$domain/ws")
                    .withJsonConversions()


                val subscription: Flow<NotesUpdateEventResponse> = session.subscribe(
                    "/topic.notes",
                    NotesUpdateEventResponse.serializer()
                )

                launch {
                    subscription.collect { noteUpdateEvent ->
                        println("MainViewModel Received: ${noteUpdateEvent.toString()}")
                        processUpdateEvents(noteUpdateEvent)
                    }
                }
            }
        }
    }

    private suspend fun processUpdateEvents(noteUpdateEvent: NotesUpdateEventResponse) {
        when (noteUpdateEvent.type) {
            UpdateType.created.name -> {
                with(noteUpdateEvent.new_note) {
                    notesQueries?.insertItem(id, title, content, created_by, created_on)
                }
            }
            UpdateType.deleted.name -> {
                with(noteUpdateEvent.new_note) {
                    notesQueries?.deleteNoteById(id)
                }
            }
            UpdateType.updated.name -> {
                with(noteUpdateEvent.new_note) {
                    notesQueries?.insertItem(id, title, content, created_by, created_on)
                }
            }
        }
    }

    suspend fun createNote(note: Note): Note {
        val noteRequestModel = noteMapper.fromDomainEntity(note)
        val noteResponse = notesAPI.createNote(noteRequestModel)
        with(noteResponse) {
            notesQueries?.insertItem(id, title, content, created_by, created_on)
        }
        return noteMapper.toDomainEntity(noteResponse)
    }

    fun findNoteById(noteId: Long) = flow {
        val note = notesQueries?.getNoteById(
            noteId,
            mapper = { id, title, content, created_by, created_on ->
                Note(title, content, created_by, created_on, id)
            })?.executeAsOneOrNull()

        emit(note)
    }

    suspend fun deleteNote(noteId: Long): Note? {
        val noteResponse = notesAPI.deleteNote(noteId)
        noteResponse?.let {
            notesQueries?.deleteNoteById(it.id)
        }
        return noteResponse?.let { noteMapper.toDomainEntity(it) }
    }

    suspend fun updateNote(newNote: Note): Note? {
        val noteRequestModel = noteMapper.fromDomainEntity(newNote)
        val noteResponse = notesAPI.updateNote(noteRequestModel)
        noteResponse?.let {
            notesQueries?.insertItem(it.id, it.title, it.content, it.created_by, it.created_on)
        }
        return noteResponse?.let { noteMapper.toDomainEntity(it) }
    }
}
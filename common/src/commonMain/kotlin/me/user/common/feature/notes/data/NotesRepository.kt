package me.user.common.feature.notes.data

import me.user.common.feature.notes.data.models.Note
import me.user.common.feature.notes.data.network.NotesAPI

class NotesRepository(private val notesAPI: NotesAPI) {

    suspend fun getAllNotes(): List<Note> {
        val notes = notesAPI.getAllNotes()
        return notes.data.notes.map {
            val (title, content, createdBy, createdOn, id) = it
            Note(
                title,
                content,
                createdBy,
                createdOn,
                id
            )
        }
    }
}
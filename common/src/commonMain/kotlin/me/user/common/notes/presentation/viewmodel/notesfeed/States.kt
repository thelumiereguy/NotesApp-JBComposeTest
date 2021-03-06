package me.user.common.notes.presentation.viewmodel.notesfeed

import me.user.common.notes.data.models.Note

sealed class States {
    object Loading : States()
    data class ShowNotes(val notes: List<Note>) : States()
}
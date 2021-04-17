package me.user.common.notes.presentation.viewmodel.update_note

import me.user.common.notes.data.models.Note

sealed class States {
    object Loading : States()
    data class ShowNote(val note: Note) : States()
}
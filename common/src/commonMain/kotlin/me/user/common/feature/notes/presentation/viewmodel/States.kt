package me.user.common.feature.notes.presentation.viewmodel

import me.user.common.feature.notes.data.models.Note

sealed class States {
    object Loading : States()
    data class ShowNotes(val notes: List<Note>) : States()
}
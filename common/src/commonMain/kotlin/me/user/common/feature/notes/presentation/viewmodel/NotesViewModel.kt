package me.user.common.feature.notes.presentation.viewmodel

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.user.common.feature.notes.data.NotesRepository

class NotesViewModel(private val notesRepo: NotesRepository) {
    private val mutableState = MutableStateFlow<States>(States.Loading)
    val state = mutableState as StateFlow<States>

    suspend fun getNotes() {
        val notes = notesRepo.getAllNotes()
        delay(2000)
        mutableState.value = States.ShowNotes(notes)
    }
}
package me.user.common.feature.notes.presentation.viewmodel

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.user.common.feature.notes.data.NotesRepository

class NotesViewModel(private val notesRepo: NotesRepository) {
    private val mutableState = MutableStateFlow<States>(States.Loading)
    val state = mutableState as StateFlow<States>

    fun getNotes() {
        GlobalScope.launch {
            val notes = notesRepo.getAllNotes()
            mutableState.value = States.ShowNotes(notes)
        }
    }
}
package me.user.common.notes.presentation.viewmodel.notesfeed

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.user.common.notes.data.NotesRepository
import kotlin.coroutines.coroutineContext

class NotesViewModel(private val notesRepo: NotesRepository) {
    private val mutableState = MutableStateFlow<States>(States.Loading)
    val state = mutableState as StateFlow<States>

    suspend fun getNotes() {
        val notes = notesRepo.getAllNotes()
        delay(2000)
        mutableState.value = States.ShowNotes(notes)
    }

    suspend fun observeChanges() {
        notesRepo.observeChanges {

            with(CoroutineScope(coroutineContext)) {
                launch {
                    getNotes()
                }
            }
        }
    }

}


package me.user.common.notes.presentation.viewmodel.notesfeed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import me.user.common.notes.data.NotesRepository

class NotesViewModel(private val notesRepo: NotesRepository) {

    val screenState = getNotesFlow()

    suspend fun getNotes() {
        withContext((Dispatchers.Default)) {
            notesRepo.getAllNotes()
        }
    }

    private fun getNotesFlow() = flow<States> {
        notesRepo.getAllNotesAsFlow()?.collect { notes ->
            emit(States.ShowNotes(notes))
        }
    }

    suspend fun observeChanges() {
        notesRepo.observeChanges()
    }

}


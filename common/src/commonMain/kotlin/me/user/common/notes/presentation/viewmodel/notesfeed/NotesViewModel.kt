package me.user.common.notes.presentation.viewmodel.notesfeed

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import me.user.common.notes.data.NotesRepository
import kotlin.coroutines.coroutineContext

class NotesViewModel(private val notesRepo: NotesRepository) {

    val state = getNotesFlow()
    private val coroutineScope: CoroutineScope = MainScope()

    init {
        coroutineScope.launch {
            getNotes()
        }
    }

    suspend fun getNotes() {
        notesRepo.getAllNotes()
    }

    private fun getNotesFlow() = flow<States> {
        notesRepo.getAllNotesAsFlow().collect { notes ->
            emit(States.ShowNotes(notes))
        }
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


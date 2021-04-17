package me.user.common.notes.presentation.viewmodel.update_note

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import me.user.common.notes.data.NotesRepository
import me.user.common.notes.presentation.viewmodel.create_note.ButtonState

class UpdateNoteViewModel(private val notesRepo: NotesRepository) {

    private val _screenState: MutableStateFlow<States> = MutableStateFlow(States.Loading)
    val screenState: StateFlow<States> = _screenState


    private val _titleTextState = MutableStateFlow("")
    val titleTextState: StateFlow<String> = _titleTextState

    private val _contentTextState = MutableStateFlow("")
    val contentTextState: StateFlow<String> = _contentTextState

    private val _buttonLoadingState = MutableStateFlow(false)

    val saveButtonState =
        combine(
            titleTextState,
            contentTextState,
            _buttonLoadingState
        ) { title, content, isLoading ->
            ButtonState(
                title.isNotEmpty() && content.isNotEmpty() && isLoading.not(),
                isLoading
            )
        }

    fun onTitleChanged(title: String) {
        _titleTextState.value = title
    }

    fun onContentChanged(content: String) {
        _contentTextState.value = content
    }

    suspend fun getNoteById(noteId: Long) {
        notesRepo.findNoteById(noteId).collect { note ->
            if (note != null) {
                onTitleChanged(note.title)
                onContentChanged(note.content)
                _screenState.value = States.ShowNote(note)
            }
        }
    }

    suspend fun saveNote(onNoteCreated: () -> Unit) {
        handleLoadingState(true)
        handleLoadingState(false)
        onNoteCreated()
    }

    private fun handleLoadingState(loading: Boolean) {
        _buttonLoadingState.value = loading
    }

}

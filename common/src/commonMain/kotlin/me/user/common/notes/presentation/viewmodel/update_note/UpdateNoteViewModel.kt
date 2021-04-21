package me.user.common.notes.presentation.viewmodel.update_note

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import me.user.common.notes.data.NotesRepository
import me.user.common.notes.presentation.viewmodel.ITextFieldStateProvider
import me.user.common.notes.presentation.viewmodel.TextFieldStateProvider
import me.user.common.notes.presentation.viewmodel.create_note.ButtonState

class UpdateNoteViewModel(
    private val notesRepo: NotesRepository,
    private val textFieldState: ITextFieldStateProvider = TextFieldStateProvider()
) : ITextFieldStateProvider by textFieldState, OnContentChangedCallBack {

    private val undoRedoHandler: UndoRedoHandler = UndoRedoHandler(this)

    private val _screenState: MutableStateFlow<States> = MutableStateFlow(States.Loading)
    val screenState: StateFlow<States> = _screenState

    private val _buttonLoadingState = MutableStateFlow(false)

    val undoRedoButtonState = undoRedoHandler.undoRedoVisibilityFlow

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

    suspend fun getNoteById(noteId: Long) {
        notesRepo.findNoteById(noteId).collect { note ->
            if (note != null) {
                textFieldState.onTitleChanged(note.title)
                textFieldState.onContentChanged(note.content)
                _screenState.value = States.ShowNote(note)
            }
        }
    }


    suspend fun updateContent() {
        handleLoadingState(true)
        if (screenState.value is States.ShowNote) {
            val note = (screenState.value as States.ShowNote).note
            val newNote = note.copy(title = titleTextState.value, content = contentTextState.value)
            notesRepo.updateNote(newNote)
        }
        handleLoadingState(false)
    }


    override fun onContentChanged(content: String) {
        undoRedoHandler.onEvent(contentTextState.value, content)
        textFieldState.onContentChanged(content)
    }

    suspend fun undoClicked() {
        undoRedoHandler.onUndoClicked()
    }

    suspend fun redoClicked() {
        undoRedoHandler.onRedoClicked()
    }

    override fun onContentUpdated(newContent: String) {
        textFieldState.onContentChanged(newContent)
    }

    private fun handleLoadingState(loading: Boolean) {
        _buttonLoadingState.value = loading
    }
}

data class UndoRedoButtonState(
    val undoEnabled: Boolean,
    val redoEnabled: Boolean
)

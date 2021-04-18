package me.user.common.notes.presentation.viewmodel.update_note

import kotlinx.coroutines.flow.*
import me.user.common.notes.data.NotesRepository
import me.user.common.notes.presentation.viewmodel.ITextFieldStateProvider
import me.user.common.notes.presentation.viewmodel.TextFieldStateProvider
import me.user.common.notes.presentation.viewmodel.create_note.ButtonState

class UpdateNoteViewModel(
    private val notesRepo: NotesRepository,
    private val undoRedoHandler: UndoRedoHandler,
    private val textFieldState: ITextFieldStateProvider = TextFieldStateProvider()
) : ITextFieldStateProvider by textFieldState {

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

    private val events: MutableStateFlow<String> =
        MutableStateFlow("")

    suspend fun observeUndoRedoState() {
        events.debounce(300L).distinctUntilChanged().collect { newContent ->
            undoRedoHandler.onEvent(newContent)
        }

        undoRedoHandler.contentFlow.collect { content ->
            textFieldState.onContentChanged(content)
        }

    }


    override fun onContentChanged(content: String) {
        events.value = contentTextState.value
        textFieldState.onContentChanged(content)
    }

    suspend fun undoClicked() {
        undoRedoHandler.onUndoClicked()
    }

    suspend fun redoClicked() {
        undoRedoHandler.onRedoClicked()
    }


//    private fun updateNote(updatedNoteBlock: Note.() -> Unit) {
//        val currentState = screenState.value
//        if (currentState is States.ShowNote) {
//            val note = currentState.note
//            note.apply(updatedNoteBlock)
//            undoStack.add(note)
//        }
//    }
}

data class UndoRedoButtonState(
    val undoEnabled: Boolean,
    val redoEnabled: Boolean
)

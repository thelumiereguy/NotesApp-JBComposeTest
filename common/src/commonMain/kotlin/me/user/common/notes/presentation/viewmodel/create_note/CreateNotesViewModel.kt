package me.user.common.notes.presentation.viewmodel.create_note

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import me.user.common.notes.data.NotesRepository
import me.user.common.notes.data.models.Note
import me.user.common.notes.presentation.viewmodel.ITextFieldStateProvider
import me.user.common.notes.presentation.viewmodel.TextFieldStateProvider
import kotlin.random.Random

class CreateNotesViewModel(
    private val notesRepo: NotesRepository,
    textFieldState: ITextFieldStateProvider = TextFieldStateProvider()
) : ITextFieldStateProvider by textFieldState {

    private val _loadingState = MutableStateFlow(false)

    val saveButtonState =
        combine(titleTextState, contentTextState, _loadingState) { title, content, isLoading ->
            ButtonState(
                title.isNotEmpty() && content.isNotEmpty() && isLoading.not(),
                isLoading
            )
        }

    suspend fun createNote(onNoteCreated: () -> Unit) {
        handleLoadingState(true)
        val title = titleTextState.value
        val content = contentTextState.value
        val author = "thelumiereguy"
        val note =
            Note(title, content, author, System.currentTimeMillis(), Random.nextInt().toLong())
        notesRepo.createNote(note)
        handleLoadingState(false)
        onNoteCreated()
    }

    private fun handleLoadingState(loading: Boolean) {
        _loadingState.value = loading
    }

}

data class ButtonState(
    val enabled: Boolean,
    val showLoading: Boolean
) {
    companion object {
        fun defaultButtonState() = ButtonState(false, false)
    }
}


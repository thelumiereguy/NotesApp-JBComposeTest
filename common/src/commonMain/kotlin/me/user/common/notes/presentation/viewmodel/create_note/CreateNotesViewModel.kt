package me.user.common.notes.presentation.viewmodel.create_note

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import me.user.common.notes.data.NotesRepository
import me.user.common.notes.data.models.Note
import kotlin.random.Random

class CreateNotesViewModel(private val notesRepo: NotesRepository) {

    private val _titleTextState = MutableStateFlow("")
    val titleTextState: StateFlow<String> = _titleTextState

    private val _contentTextState = MutableStateFlow("")
    val contentTextState: StateFlow<String> = _contentTextState

    private val _loadingState = MutableStateFlow(false)

    val saveButtonState =
        combine(titleTextState, contentTextState, _loadingState) { title, content, isLoading ->
            ButtonState(
                title.isNotEmpty() && content.isNotEmpty() && isLoading.not(),
                isLoading
            )
        }

    init {

    }

    fun onTitleChanged(title: String) {
        _titleTextState.value = title
    }

    fun onContentChanged(content: String) {
        _contentTextState.value = content
    }

    suspend fun createNote(onNoteCreated: () -> Unit) {
        handleLoadingState(true)
        val title = titleTextState.value
        val content = contentTextState.value
        val author = "thelumiereguy"
        val note = Note(title, content, author, System.currentTimeMillis(), Random.nextInt().toLong())
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


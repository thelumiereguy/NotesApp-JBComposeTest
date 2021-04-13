package me.user.common.notes.presentation.viewmodel.create_note

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import me.user.common.notes.data.NotesRepository

class CreateNotesViewModel(private val notesRepo: NotesRepository) {

    private val _titleTextState = MutableStateFlow("")
    val titleTextState: StateFlow<String> = _titleTextState

    private val _contentTextState = MutableStateFlow("")
    val contentTextState: StateFlow<String> = _contentTextState


    val saveButtonState = combine(titleTextState, contentTextState) { title, content ->
        title.isNotEmpty() && content.isNotEmpty()
    }

    fun onTitleChanged(title: String) {
        _titleTextState.value = title
    }

    fun onContentChanged(content: String) {
        _contentTextState.value = content
    }

}


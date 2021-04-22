/*
 * Created by Piyush Pradeepkumar on 22/04/21, 1:14 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.presentation.viewmodel.update_note

import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FeatherIcons
import compose.icons.feathericons.X
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.user.common.notes.data.NotesRepository

class UpdateNoteOptionsViewmodel(private val notesRepo: NotesRepository) {

    private val _deleteOptionState: MutableStateFlow<OptionState> = MutableStateFlow(
        OptionState(
            "Delete Note",
            FeatherIcons.X,
            false
        )
    )
    val deleteOptionState: StateFlow<OptionState> = _deleteOptionState

    suspend fun deleteNote(noteId: Long, onSuccess: () -> Unit) {
        _deleteOptionState.value = deleteOptionState.value.copy(isLoading = true)
        val response = notesRepo.deleteNote(noteId)
        response?.let { onSuccess() }
        _deleteOptionState.value = deleteOptionState.value.copy(isLoading = false)
    }
}

data class OptionState(
    val name: String,
    val icon: ImageVector,
    val isLoading: Boolean,
)
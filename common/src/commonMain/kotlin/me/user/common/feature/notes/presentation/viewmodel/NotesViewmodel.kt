package me.user.common.feature.notes.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow

class NotesViewmodel {
    val mutableState = MutableStateFlow(States.Loading)
}
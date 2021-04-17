package me.user.common.notes.presentation.routes

sealed class RouterActions {
    object PopBackStack : RouterActions()
    object RouteToCreateNote : RouterActions()
    data class ShowOptionsDialog(val url: String) : RouterActions()
}
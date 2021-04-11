package me.user.common.notes.presentation.routes

sealed class NavigationActions {
    object PopBackStack : NavigationActions()
    object RouteToCreateNote : NavigationActions()
}
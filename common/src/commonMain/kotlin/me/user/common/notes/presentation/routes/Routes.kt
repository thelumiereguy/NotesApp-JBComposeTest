package me.user.common.notes.presentation.routes

enum class Routes(val url: String) {
    HOME("/home"),
    CREATE_NOTE("/create"),
    UPDATE_NOTE("/update/{id:[0-9]+}?")
}
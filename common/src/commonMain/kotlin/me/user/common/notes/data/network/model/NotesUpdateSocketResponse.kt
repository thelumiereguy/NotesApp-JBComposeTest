package me.user.common.notes.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotesUpdateEventResponse(
    @SerialName("new_note")
    val new_note: NoteResponse,
    @SerialName("type")
    val type: String
)

enum class UpdateType {
    created,
    deleted,
    updated
}

package me.user.common.notes.data.mapper

import me.user.common.notes.data.models.Note
import me.user.common.notes.data.network.model.NoteDTO

class NoteMapper {
    fun fromDomainEntity(note: Note): NoteDTO {
        return with(note) {
            NoteDTO(
                title, content, created_by, created_on, id
            )
        }
    }

    fun toDomainEntity(noteDTO: NoteDTO): Note {
        return with(noteDTO) {
            Note(
                title, content, created_by, created_on, id
            )
        }
    }
}
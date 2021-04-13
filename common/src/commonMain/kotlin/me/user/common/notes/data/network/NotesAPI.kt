package me.user.common.notes.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*
import io.ktor.http.contentType
import me.user.common.notes.data.network.model.GenericResponseWrapper
import me.user.common.notes.data.network.model.GetNotesResponseDTO
import me.user.common.notes.data.network.model.NoteDTO
import me.user.common.notes.data.network.model.UpdateResponseDTO

class NotesAPI(private val client: HttpClient) {

    private val baseURL = "http://$IP/api"

    suspend fun getAllNotes() =
        client.get<GenericResponseWrapper<GetNotesResponseDTO>>("$baseURL/getNotes")

    suspend fun createNote(noteRequestModel: NoteDTO): NoteDTO {
        val response = client.post<GenericResponseWrapper<UpdateResponseDTO>>("$baseURL/createNote") {
            contentType(ContentType.Application.Json)
            body = noteRequestModel
        }
        return response.data.note
    }
}

const val IP = "192.168.0.107:8080"
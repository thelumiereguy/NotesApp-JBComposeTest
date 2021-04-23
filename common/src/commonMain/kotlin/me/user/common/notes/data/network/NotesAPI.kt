package me.user.common.notes.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.user.common.notes.data.network.model.GenericResponseWrapper
import me.user.common.notes.data.network.model.GetNotesResponseDTO
import me.user.common.notes.data.network.model.NoteDTO
import me.user.common.notes.data.network.model.UpdateResponseDTO

class NotesAPI(private val client: HttpClient) {

    private val baseURL = "http://$domain/api"

    suspend fun getAllNotes() =
        client.get<GenericResponseWrapper<GetNotesResponseDTO>>("$baseURL/getNotes")

    suspend fun createNote(noteRequestModel: NoteDTO): NoteDTO {
        val response =
            client.post<GenericResponseWrapper<UpdateResponseDTO>>("$baseURL/createNote") {
                contentType(ContentType.Application.Json)
                body = noteRequestModel
            }
        return response.data.note
    }

    suspend fun deleteNote(noteId: Long): NoteDTO? {
        return try {
            val response =
                client.delete<GenericResponseWrapper<UpdateResponseDTO>>("$baseURL/deleteNote/$noteId") {
                    contentType(ContentType.Application.Json)
                }
            response.data.note
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateNote(noteRequestModel: NoteDTO): NoteDTO? {
        return try {
            val response =
                client.put<GenericResponseWrapper<UpdateResponseDTO>>("$baseURL/updateNote") {
                    contentType(ContentType.Application.Json)
                    body = noteRequestModel
                }
            response.data.note
        } catch (e: Exception) {
            null
        }
    }
}

const val domain = "192.168.43.176:8080"
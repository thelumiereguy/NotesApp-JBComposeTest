package me.user.common.feature.notes.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import me.user.common.feature.notes.data.network.model.GenericResponseWrapper
import me.user.common.feature.notes.data.network.model.GetNotesResponseDTO

class NotesAPI(private val client: HttpClient) {

    private val baseURL = "http://192.168.0.107:8080/api"

    suspend fun getAllNotes() =
        client.get<GenericResponseWrapper<GetNotesResponseDTO>>("$baseURL/getNotes")
}
package me.user.common.notes.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import me.user.common.notes.data.network.model.GenericResponseWrapper
import me.user.common.notes.data.network.model.GetNotesResponseDTO

class NotesAPI(private val client: HttpClient) {

    private val baseURL = "http://$IP/api"

    suspend fun getAllNotes() =
        client.get<GenericResponseWrapper<GetNotesResponseDTO>>("$baseURL/getNotes")
}

const val IP = "192.168.0.107:8080"
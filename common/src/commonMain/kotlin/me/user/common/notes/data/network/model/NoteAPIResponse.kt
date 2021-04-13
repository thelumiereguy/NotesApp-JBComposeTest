/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:47 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenericResponseWrapper<T>(val data: T)

@Serializable
data class GetNotesResponseDTO(val notes: List<NoteDTO>)

@Serializable
data class NoteDTO(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("created_by")
    val created_by: String,
    @SerialName("created_on")
    val created_on: Long,
    @SerialName("id")
    val id: Long = 0,
)

@Serializable
data class UpdateResponseDTO(
    @SerialName("message")
    val message: String,
    @SerialName("note")
    val note: NoteDTO,
)
/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:47 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.feature.notes.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Note(
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
/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:47 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.feature.notes.data.models


data class Note(
    val title: String,
    val content: String,
    val created_by: String,
    val created_on: Long,
    val createdOnText: String,
    val id: Long = 0,
)
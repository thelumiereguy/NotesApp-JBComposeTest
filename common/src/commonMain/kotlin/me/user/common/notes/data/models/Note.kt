/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:47 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.data.models

import java.text.SimpleDateFormat
import java.util.*


data class Note(
    val title: String,
    val content: String,
    val created_by: String,
    val created_on: Long,
    val id: Long = 0,
) {
    val createdOnText = formatDate(created_on)

    private fun formatDate(date: Long): String {
        return SimpleDateFormat("dd MMMM", Locale.getDefault()).format(date)
    }
}
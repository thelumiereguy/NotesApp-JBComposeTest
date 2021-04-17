/*
 * Created by Piyush Pradeepkumar on 17/04/21, 12:32 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.presentation.composeables.notesfeed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.user.common.notes.data.models.Note

@ExperimentalFoundationApi
@Composable
fun NoteItem(
    note: Note,
    actions: NoteItemActions
) {
    Card(
        modifier = Modifier.padding(8.dp).combinedClickable(
            onLongClick = { actions.onLongClick(note) },
            onClick = { actions.onClick(note) }
        ),
        shape = RoundedCornerShape(14.dp),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 12.dp,
    ) {
        Box {
            Column(
                Modifier.padding(8.dp)
            ) {
                Text(
                    note.title,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight(700),
                    fontSize = 18.sp
                )

                Text(
                    note.content,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                    color = MaterialTheme.colors.onPrimary
                )

                Row(Modifier.fillMaxWidth()) {
                    Text(note.createdOnText, color = MaterialTheme.colors.onPrimary)

                    Text(
                        note.created_by,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = MaterialTheme.colors.onPrimary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

class NoteItemActions(
    val onClick: (Note) -> Unit,
    val onLongClick: (Note) -> Unit
)
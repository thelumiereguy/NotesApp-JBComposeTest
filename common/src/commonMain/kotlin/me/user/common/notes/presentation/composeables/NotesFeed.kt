package me.user.common.notes.presentation.composeables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
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
fun NotesFeed(notesList: List<Note>, onItemSelected: (Note) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
    ) {
        items(notesList) {
            NoteItem(it, onItemSelected)
        }
    }
}

@Composable
fun NoteItem(note: Note, onItemSelected: (Note) -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp).clickable(
            onClick = { onItemSelected(note) }
        ),
        shape = RoundedCornerShape(14.dp),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 12.dp,
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            Text(
                note.title,
                color = colors.onPrimary,
                fontWeight = FontWeight(700),
                fontSize = 18.sp
            )

            Text(
                note.content,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                color = colors.onPrimary
            )

            Row(Modifier.fillMaxWidth()) {
                Text(note.createdOnText, color = colors.onPrimary)

                Text(
                    note.created_by,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = colors.onPrimary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}
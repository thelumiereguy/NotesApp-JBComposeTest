package me.user.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetsnack.ui.theme.NotesTheme
import kotlinx.coroutines.launch
import me.user.common.feature.notes.data.models.Note
import me.user.common.feature.notes.presentation.theme.SecondaryTextColor
import me.user.common.feature.notes.presentation.viewmodel.NotesViewModel
import me.user.common.feature.notes.presentation.viewmodel.States
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.core.Koin

@ExperimentalFoundationApi
@Composable
fun NotesApp(
    koin: Koin
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel =
        remember {
            koin.get<NotesViewModel>().also {
                coroutineScope.launch { it.getNotes() }
                coroutineScope.launch { it.observeChanges() }
            }
        }

    val state by mutableStateOf(viewModel.state.collectAsState())

    val navigator = rememberNavigator()

    NotesTheme {
        NavHost(
            navigator = navigator,
            initialRoute = "/home"
        ) {
            scene(route = "/home") {
                Scaffold(
                    backgroundColor = MaterialTheme.colors.surface
                ) {
                    Column(
                        Modifier.fillMaxWidth()
                            .fillMaxHeight(),
                    ) {
                        Box(Modifier.padding(PaddingValues(12.dp, vertical = 8.dp))) {
                            Text(
                                "Notes",
                                color = Color.White,
                                fontWeight = FontWeight(900),
                                fontSize = 24.sp
                            )
                        }

                        val currentState = state.value

                        when (currentState) {
                            States.Loading -> {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.requiredSize(64.dp))
                                }
                            }
                            is States.ShowNotes -> {
                                NotesFeed(currentState.notes) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

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
        modifier = Modifier.padding(8.dp).shadow(12.dp).clickable(
            onClick = { onItemSelected(note) }
        ),
        shape = RoundedCornerShape(14.dp),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 12.dp,
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            Text(note.title, color = Color.White, fontWeight = FontWeight(700), fontSize = 18.sp)

            Text(
                note.content,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                color = SecondaryTextColor
            )

            Row(Modifier.fillMaxWidth()) {
                Text(note.createdOnText, color = SecondaryTextColor)

                Text(
                    note.created_by,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = SecondaryTextColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

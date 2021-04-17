package me.user.common.notes.presentation.composeables.notesfeed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import me.user.common.notes.data.models.Note
import me.user.common.notes.presentation.routes.RouterActions
import me.user.common.notes.presentation.viewmodel.notesfeed.NotesViewModel
import me.user.common.notes.presentation.viewmodel.notesfeed.States

@ExperimentalFoundationApi
@Composable
fun NotesFeed(
    viewModel: NotesViewModel,
    navigationActions: (RouterActions) -> Unit
) {
    val fabShape = RoundedCornerShape(50)

    val coroutineScope = rememberCoroutineScope()

    viewModel.run {
        coroutineScope.launch { getNotes() }
        coroutineScope.launch { observeChanges() }
    }

    val state = viewModel.screenState.collectAsState(States.Loading)

    val dropDownState = remember { mutableStateOf(false) }

    when (val currentState = state.value) {
        States.Loading -> {
            Loading(colors)
        }
        is States.ShowNotes -> {
            Scaffold(
                backgroundColor = colors.surface,
                bottomBar = {
                    BottomAppBar(
                        cutoutShape = fabShape,
                        elevation = 8.dp,
                        backgroundColor = colors.primary
                    ) {

                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {},
                        shape = fabShape,
                        backgroundColor = colors.secondary
                    ) {
                        IconButton(onClick = {
                            navigationActions(RouterActions.RouteToCreateNote)
                        }) {
                            Icon(imageVector = Icons.Filled.Add, "", tint = colors.onPrimary)
                        }
                    }
                },
                isFloatingActionButtonDocked = true,
                floatingActionButtonPosition = FabPosition.End,
            ) { innerPadding ->
                Column {
                    Box(
                        Modifier.padding(
                            PaddingValues(
                                top = 16.dp,
                                bottom = 8.dp,
                                start = 16.dp
                            )
                        )
                    ) {
                        Text(
                            "Notes",
                            color = colors.onPrimary,
                            fontWeight = FontWeight(900),
                            fontSize = 24.sp
                        )
                    }
                    NotesList(
                        currentState.notes,
                        innerPadding,
                        NoteItemActions(
                            onClick = {

                            },
                            onLongClick = {

                            }
                        )
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun NotesList(
    notesList: List<Note>,
    paddingValues: PaddingValues,
    noteItemActions: NoteItemActions
) {
    Box {
        val scrollState = rememberLazyListState()

        LazyVerticalGrid(
            cells = GridCells.Adaptive(150.dp),
            state = scrollState,
            contentPadding = paddingValues
        ) {
            items(notesList) {
                NoteItem(it, noteItemActions)
            }
        }

        if (!scrollState.isScrolledToTheEnd()) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                colors.surface
                            )
                        )
                    ).align(Alignment.BottomCenter)
            )
        }
    }
}

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
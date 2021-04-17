package me.user.common.notes.presentation.composeables.update_note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.FontAwesomeIcons
import compose.icons.feathericons.ChevronLeft
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.EllipsisV
import compose.icons.fontawesomeicons.solid.Redo
import compose.icons.fontawesomeicons.solid.Undo
import kotlinx.coroutines.launch
import me.user.common.notes.presentation.composeables.create_note.NoteTextField
import me.user.common.notes.presentation.composeables.notesfeed.Loading
import me.user.common.notes.presentation.routes.RouterActions
import me.user.common.notes.presentation.viewmodel.create_note.ButtonState
import me.user.common.notes.presentation.viewmodel.update_note.States
import me.user.common.notes.presentation.viewmodel.update_note.UpdateNoteViewModel


@Composable
fun UpdateNote(
    noteId: Long,
    updateNoteViewModel: UpdateNoteViewModel,
    routerActions: (RouterActions) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    updateNoteViewModel.run {
        coroutineScope.launch {
            getNoteById(noteId)
        }
    }

    val state = updateNoteViewModel.screenState.collectAsState()

    when (state.value) {
        States.Loading -> {
            Loading(MaterialTheme.colors)
        }
        is States.ShowNote -> {
            Scaffold(
                backgroundColor = MaterialTheme.colors.surface,
            ) {
                Box(
                    Modifier.fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    Column {
                        Row(
                            Modifier.padding(
                                PaddingValues(
                                    top = 8.dp,
                                    start = 8.dp
                                )
                            ).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick = {
                                routerActions(RouterActions.PopBackStack)
                            }) {
                                Icon(
                                    imageVector = FeatherIcons.ChevronLeft,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.onPrimary,
                                )
                            }

                            Text(
                                "Update Note",
                                color = MaterialTheme.colors.onPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 24.sp
                            )

                            Box {
                                ToolbarIcons(routerActions, updateNoteViewModel)
                            }
                        }

                        Column(
                            Modifier.padding(8.dp),
                        ) {

                            val titleTextState = updateNoteViewModel.titleTextState.collectAsState()

                            val saveButtonState =
                                updateNoteViewModel.saveButtonState.collectAsState(ButtonState.defaultButtonState())

                            NoteTextField(
                                titleTextState.value,
                                "Enter Title",
                                updateNoteViewModel::onTitleChanged,
                                FontWeight.Bold,
                                isSingleLine = true
                            )


                            val contentState = updateNoteViewModel.contentTextState.collectAsState()

                            NoteTextField(
                                contentState.value,
                                "Enter Content",
                                updateNoteViewModel::onContentChanged,
                                FontWeight.SemiBold,
                                Modifier.weight(1F).fillMaxWidth().padding(8.dp),
                                false
                            )

                            Button(
                                enabled = saveButtonState.value.enabled,
                                content = {
                                    if (saveButtonState.value.showLoading) {
                                        CircularProgressIndicator(
                                            color = MaterialTheme.colors.onPrimary
                                        )
                                    } else {
                                        Text(
                                            "Save",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.secondary,
                                    contentColor = MaterialTheme.colors.onPrimary
                                ),
                                onClick = {
                                    coroutineScope.launch {
                                        updateNoteViewModel.saveNote {
                                            routerActions(RouterActions.PopBackStack)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToolbarIcons(routerActions: (RouterActions) -> Unit, updateNoteViewModel: UpdateNoteViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {
            routerActions(RouterActions.PopBackStack)
        }, modifier = Modifier) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.Undo,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }

        IconButton(onClick = {
            routerActions(RouterActions.PopBackStack)
        }, modifier = Modifier) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.Redo,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }

        IconButton(onClick = {
            routerActions(RouterActions.PopBackStack)
        }, modifier = Modifier) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.EllipsisV,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

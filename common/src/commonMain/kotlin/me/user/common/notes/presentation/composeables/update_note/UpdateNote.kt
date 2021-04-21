package me.user.common.notes.presentation.composeables.update_note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import me.user.common.notes.presentation.viewmodel.update_note.UndoRedoButtonState
import me.user.common.notes.presentation.viewmodel.update_note.UpdateNoteOptionsViewmodel
import me.user.common.notes.presentation.viewmodel.update_note.UpdateNoteViewModel


@ExperimentalMaterialApi
@Composable
fun UpdateNote(
    noteId: Long,
    optionsViewmodel: UpdateNoteOptionsViewmodel,
    updateNoteViewModel: UpdateNoteViewModel,
    routerActions: (RouterActions) -> Unit
) {

    LaunchedEffect(noteId) {
        updateNoteViewModel.getNoteById(noteId)
    }

    val state = updateNoteViewModel.screenState.collectAsState()

    when (val currentState = state.value) {
        States.Loading -> {
            Loading(MaterialTheme.colors)
        }
        is States.ShowNote -> {

            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

            BottomSheetScaffold(
                backgroundColor = MaterialTheme.colors.surface,
                scaffoldState = bottomSheetScaffoldState,
                sheetShape = MaterialTheme.shapes.medium,
                sheetPeekHeight = 0.dp,
                sheetBackgroundColor = Color.Transparent,
                sheetContent = {
                    OptionsBottomSheet(
                        currentState.note,
                        optionsViewmodel,
                        routerActions,
                        toggleBottomSheetState = {
                            toggleBottomSheet(bottomSheetScaffoldState)
                        }
                    )
                }
            ) {
                Box(
                    Modifier.fillMaxWidth()
                        .fillMaxHeight(),
                ) {

                    val coroutineScope = rememberCoroutineScope()

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
                            Row(verticalAlignment = Alignment.CenterVertically) {
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
                            }


                            ToolbarIcons(updateNoteViewModel) {
                                coroutineScope.launch {
                                    toggleBottomSheet(bottomSheetScaffoldState)
                                }
                            }
                        }

                        UpdateNoteContent(updateNoteViewModel, routerActions)
                    }
                }
            }
        }
    }
}

@Composable
private fun UpdateNoteContent(
    updateNoteViewModel: UpdateNoteViewModel,
    routerActions: (RouterActions) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

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
                        "Update note",
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

@Composable
fun ToolbarIcons(updateNoteViewModel: UpdateNoteViewModel, onMenuClick: () -> Unit) {

    val undoRedoButtonState = updateNoteViewModel.undoRedoButtonState.collectAsState(
        UndoRedoButtonState(false, false)
    )

    val coroutineScope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {
            coroutineScope.launch {
                updateNoteViewModel.undoClicked()
            }
        }, modifier = Modifier, enabled = undoRedoButtonState.value.undoEnabled) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.Undo,
                contentDescription = "Undo",
                tint = getButtonColor(undoRedoButtonState.value.undoEnabled),
                modifier = Modifier.size(16.dp)
            )
        }

        IconButton(onClick = {
            coroutineScope.launch {
                updateNoteViewModel.redoClicked()
            }
        }, modifier = Modifier, enabled = undoRedoButtonState.value.redoEnabled) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.Redo,
                contentDescription = "Redo",
                tint = getButtonColor(undoRedoButtonState.value.redoEnabled),
                modifier = Modifier.size(16.dp)
            )
        }

        IconButton(onClick = onMenuClick, modifier = Modifier) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.EllipsisV,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun getButtonColor(enabled: Boolean): Color {
    return if (enabled) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface.copy(
        alpha = ContentAlpha.disabled
    )
}


@ExperimentalMaterialApi
private suspend fun toggleBottomSheet(bottomSheetState: BottomSheetScaffoldState) {
    if (bottomSheetState.bottomSheetState.isExpanded) {
        bottomSheetState.bottomSheetState.collapse()
    } else {
        bottomSheetState.bottomSheetState.expand()
    }
}

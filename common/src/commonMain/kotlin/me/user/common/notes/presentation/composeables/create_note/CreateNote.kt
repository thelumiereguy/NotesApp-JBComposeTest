package me.user.common.notes.presentation.composeables.create_note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronLeft
import kotlinx.coroutines.launch
import me.user.common.notes.presentation.routes.RouterActions
import me.user.common.notes.presentation.viewmodel.create_note.ButtonState
import me.user.common.notes.presentation.viewmodel.create_note.CreateNotesViewModel


@Composable
fun CreateNote(
    createViewModel: CreateNotesViewModel,
    navigationActions: (RouterActions) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        backgroundColor = colors.surface,
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
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navigationActions(RouterActions.PopBackStack)
                    }) {
                        Icon(
                            imageVector = FeatherIcons.ChevronLeft,
                            contentDescription = null,
                            tint = colors.onPrimary,
                        )
                    }

                    Text(
                        "Create Note",
                        color = colors.onPrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp
                    )
                }

                Column(
                    Modifier.padding(8.dp),
                ) {

                    val titleTextState = createViewModel.titleTextState.collectAsState()

                    val saveButtonState =
                        createViewModel.saveButtonState.collectAsState(ButtonState.defaultButtonState())

                    NoteTextField(
                        titleTextState.value,
                        "Enter Title",
                        createViewModel::onTitleChanged,
                        FontWeight.Bold,
                        isSingleLine = true
                    )


                    val contentState = createViewModel.contentTextState.collectAsState()

                    NoteTextField(
                        contentState.value,
                        "Enter Content",
                        createViewModel::onContentChanged,
                        FontWeight.SemiBold,
                        Modifier.weight(1F).fillMaxWidth().padding(8.dp),
                        false
                    )

                    Button(
                        enabled = saveButtonState.value.enabled,
                        content = {
                            if (saveButtonState.value.showLoading) {
                                CircularProgressIndicator(
                                    color = colors.onPrimary
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
                            backgroundColor = colors.secondary,
                            contentColor = colors.onPrimary
                        ),
                        onClick = {
                            coroutineScope.launch {
                                createViewModel.createNote {
                                    navigationActions(RouterActions.PopBackStack)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteTextField(
    text: String,
    label: String,
    onTextChanged: (String) -> Unit,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier.fillMaxWidth().padding(8.dp),
    isSingleLine: Boolean
) {
    TextField(
        text,
        onValueChange = onTextChanged,
        modifier = modifier,
        label = {
            Text(
                text = label,
                style = TextStyle(
                    fontWeight = fontWeight
                ),
                fontSize = 16.sp
            )
        },
        shape = RoundedCornerShape(10.dp),
        singleLine = isSingleLine,
        textStyle = TextStyle(
            color = colors.onSurface,
            fontWeight = fontWeight,
            fontSize = 20.sp
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = colors.onPrimary,
            unfocusedLabelColor = colors.onPrimary,
            cursorColor = colors.secondary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}
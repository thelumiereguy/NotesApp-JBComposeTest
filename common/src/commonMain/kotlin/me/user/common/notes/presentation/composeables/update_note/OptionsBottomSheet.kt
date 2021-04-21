/*
 * Created by Piyush Pradeepkumar on 22/04/21, 12:21 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.presentation.composeables.update_note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.X
import kotlinx.coroutines.launch

@Composable
fun OptionsBottomSheet(
    toggleBottomSheetState: suspend () -> Unit,
    bottomSheetActions: suspend (BottomSheetActions) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                coroutineScope.launch {
                    toggleBottomSheetState()
                }
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            backgroundColor = MaterialTheme.colors.onPrimary,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            ) {
                Text(
                    "Options",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 24.sp,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().clickable {
                        coroutineScope.launch {
                            bottomSheetActions(BottomSheetActions.DeleteNote)
                        }
                    }.padding(16.dp)
                ) {
                    Icon(
                        imageVector = FeatherIcons.X,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        "Delete Note",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

sealed class BottomSheetActions {
    object DeleteNote : BottomSheetActions()
}
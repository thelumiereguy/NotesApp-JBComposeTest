/*
 * Created by Piyush Pradeepkumar on 17/04/21, 12:32 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.presentation.composeables.notesfeed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun Loading(colors: Colors) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(colors.surface),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.requiredSize(64.dp),
            color = colors.secondary
        )
    }
}

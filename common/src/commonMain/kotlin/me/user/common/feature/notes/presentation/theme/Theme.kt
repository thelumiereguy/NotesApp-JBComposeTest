package com.example.jetsnack.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import me.user.common.feature.notes.presentation.theme.BackgroundColor
import me.user.common.feature.notes.presentation.theme.PrimaryColor
import me.user.common.feature.notes.presentation.theme.SecondaryColor


private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    surface = BackgroundColor
)

@Composable
fun NotesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography(),
        content = content
    )
}


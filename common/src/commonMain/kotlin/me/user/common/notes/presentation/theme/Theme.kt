package me.user.common.notes.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    surface = SurfaceColor,
    onPrimary = PrimaryTextColor,
    onSurface = PrimaryTextColor
)

@Composable
fun NotesTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography(),
        content = content
    )
}


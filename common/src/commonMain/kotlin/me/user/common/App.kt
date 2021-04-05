package me.user.common

import android.widget.StackView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import me.user.common.feature.notes.presentation.viewmodel.NotesViewModel
import me.user.common.feature.notes.presentation.viewmodel.States

@Composable
fun App(
    viewmodel: NotesViewModel,
    onLaunched: () -> Unit,
    onClick: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val state by mutableStateOf(viewmodel.state.collectAsState())
    val unit = remember { coroutineScope.launch { viewmodel.getNotes() } }

    MaterialTheme {
        if (state.value == States.Loading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                (state.value as States.ShowNotes).notes.forEach {
                    Text(it.content)
                }
            }
        }

    }
    onLaunched()
}

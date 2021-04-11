package me.user.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.user.common.notes.presentation.composeables.notesfeed.NotesFeed
import me.user.common.notes.presentation.routes.NavigationActions
import me.user.common.notes.presentation.routes.Routes
import me.user.common.notes.presentation.theme.NotesTheme
import me.user.common.notes.presentation.viewmodel.feed.NotesViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.core.Koin

@ExperimentalFoundationApi
@Composable
fun NotesApp(
    koin: Koin
) {
    val navigator = rememberNavigator()

    NotesTheme {
        NavHost(
            navigator = navigator,
            initialRoute = Routes.HOME.url
        ) {

            scene(route = Routes.HOME.url) {
                val viewModel = remember {
                    koin.get<NotesViewModel>()
                }
                NotesFeed(viewModel, navigator::processEvents)
            }

            scene(route = Routes.CREATE_NOTE.url) {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(onClick = {
                                    navigator.processEvents(NavigationActions.PopBackStack)
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.onPrimary,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            },
                            title = {
                                Text(
                                    "Create Note",
                                    color = Color.White,
                                    fontWeight = FontWeight(900),
                                    fontSize = 24.sp
                                )
                            },
                            backgroundColor = colors.primary
                        )
                    },
                    backgroundColor = colors.primary
                ) {

                }
            }
        }
    }
}

fun Navigator.processEvents(navigationActions: NavigationActions) {
    when (navigationActions) {
        NavigationActions.PopBackStack -> if (canGoBack) goBack()
        NavigationActions.RouteToCreateNote -> navigate(
            Routes.CREATE_NOTE.url
        )
    }
}


package me.user.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronLeft
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

            scene(
                route = Routes.CREATE_NOTE.url
            ) {
                Scaffold(
                    backgroundColor = colors.surface,
                ) {
                    Column(
                        Modifier.fillMaxWidth()
                            .fillMaxHeight(),
                    ) {
                        Row(
                            Modifier.padding(
                                PaddingValues(
                                    top = 8.dp,
                                    bottom = 8.dp,
                                    start = 8.dp
                                )
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                navigator.processEvents(NavigationActions.PopBackStack)
                            }) {
                                Icon(
                                    imageVector = FeatherIcons.ChevronLeft,
                                    contentDescription = null,
                                    tint = colors.onPrimary,
                                )
                            }

                            Text(
                                "Create Note",
                                color = Color.White,
                                fontWeight = FontWeight(900),
                                fontSize = 24.sp
                            )
                        }

                        Column(
                            Modifier.padding(8.dp),
                        ) {
                            val mutableState = remember { mutableStateOf("Enter Title") }
                            TextField(
                                mutableState.value,
                                onValueChange = {
                                    mutableState.value = it
                                },
                                singleLine = true,
                            )

                            val contentState = remember { mutableStateOf("Enter Content") }

                            TextField(
                                contentState.value,
                                onValueChange = {
                                    contentState.value = it
                                },
                                modifier = Modifier.weight(1F)
                            )

                            Button(
                                content = {
                                    Text("Create")
                                },
                                shape = RoundedCornerShape(8.dp),
                                onClick = {

                                }
                            )
                        }
                    }
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


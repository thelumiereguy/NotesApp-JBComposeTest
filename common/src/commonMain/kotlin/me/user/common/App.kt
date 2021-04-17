package me.user.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import me.user.common.notes.presentation.composeables.create_note.CreateNote
import me.user.common.notes.presentation.composeables.notesfeed.NotesFeed
import me.user.common.notes.presentation.composeables.update_note.UpdateNote
import me.user.common.notes.presentation.routes.RouterActions
import me.user.common.notes.presentation.routes.Routes
import me.user.common.notes.presentation.theme.NotesTheme
import me.user.common.notes.presentation.viewmodel.create_note.CreateNotesViewModel
import me.user.common.notes.presentation.viewmodel.notesfeed.NotesViewModel
import me.user.common.notes.presentation.viewmodel.update_note.UpdateNoteViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.route.scene
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
                val viewModel = remember {
                    koin.get<CreateNotesViewModel>()
                }
                CreateNote(viewModel, navigator::processEvents)
            }

            scene(
                route = Routes.UPDATE_NOTE.url
            ) {
                it.path<Long>("id")?.let { noteId ->
                    val viewModel = remember {
                        koin.get<UpdateNoteViewModel>()
                    }
                    UpdateNote(noteId, viewModel, navigator::processEvents)
                }
            }
        }
    }
}

fun Navigator.processEvents(routerActions: RouterActions) {
    when (routerActions) {
        RouterActions.PopBackStack -> if (canGoBack) goBack()
        RouterActions.RouteToCreateNote -> navigate(
            Routes.CREATE_NOTE.url
        )
        is RouterActions.ShowUpdateNoteScreen -> {
            navigate("/update/${routerActions.noteId}") // See Routes.UPDATE_NOTE.url
        }
    }
}


import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import me.user.common.NotesApp
import me.user.common.di.initKoin

val koinContainer = initKoin().koin

fun main() = Window(
    size = IntSize(1280, 720),
    onDismissRequest = {

    }
) {
    NotesApp(koinContainer.get(),
        {

        })
}
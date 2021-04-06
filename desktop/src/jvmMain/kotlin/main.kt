import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.unit.IntSize
import me.user.common.NotesApp
import me.user.common.di.initKoin


@ExperimentalFoundationApi
fun main() = Window(
    size = IntSize(1280, 720),
) {
    val koinContainer = initKoin().koin
    NotesApp(koinContainer)
}
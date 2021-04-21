import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.IntSize
import me.user.common.NotesApp
import me.user.common.di.initKoin
import moe.tlaster.precompose.PreComposeWindow


@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun main() = PreComposeWindow(
    size = IntSize(800, 800),
) {
    val koinContainer = initKoin().koin
    NotesApp(koinContainer)
}
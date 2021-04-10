import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.unit.IntSize
import me.user.common.IDbDependencies
import me.user.common.NotesApp
import me.user.common.di.initKoin
import me.user.common.getDbClient
import moe.tlaster.precompose.PreComposeWindow
import org.koin.dsl.module


@ExperimentalFoundationApi
fun main() = PreComposeWindow(
    size = IntSize(800, 800),
) {
    val koinContainer = initKoin(module = module {
        single { getDbClient(object : IDbDependencies {}) }
    }).koin
    NotesApp(koinContainer)
}
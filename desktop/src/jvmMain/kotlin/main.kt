import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import me.user.common.App

fun main() = Window(
    size = IntSize(1980, 1080),
    onDismissRequest = {

    }
) {
    App({

    })
}
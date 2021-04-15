package me.user.common.notes.presentation.composeables.notesfeed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.user.common.notes.data.models.Note


@Composable
fun LazyVerticalGrid(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: LazyListScope.() -> Unit,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
) {

}

@ExperimentalFoundationApi
@Composable
fun Test(
    notesList: List<Note>,
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(notesList) {
            NoteItem(it) {}
        }
    }

//    val rows = (scope.totalSize + nColumns - 1) / nColumns
//    LazyColumn(
//        modifier = modifier,
//        state = state,
//        contentPadding = contentPadding
//    ) {
//        items(rows) { rowIndex ->
//            Row {
//                for (columnIndex in 0 until nColumns) {
//                    val itemIndex = rowIndex * nColumns + columnIndex
//                    if (itemIndex < scope.totalSize) {
//                        Box(
//                            modifier = Modifier.weight(1f, fill = true),
//                            propagateMinConstraints = true
//                        ) {
//                            scope.contentFor(itemIndex, this@items).invoke()
//                        }
//                    } else {
//                        Spacer(Modifier.weight(1f, fill = true))
//                    }
//                }
//            }
//        }
//    }
}



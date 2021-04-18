/*
 * Created by Piyush Pradeepkumar on 18/04/21, 8:40 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.presentation.viewmodel.update_note

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.*

class UndoRedoHandler {

    private val undoStack = Stack<String>()
    private val redoStack = Stack<String>()

    private val _undoRedoVisibilityFlow = MutableSharedFlow<UndoRedoButtonState>()
    val undoRedoVisibilityFlow: SharedFlow<UndoRedoButtonState> = _undoRedoVisibilityFlow


    /**
     * Events from Keyboard are added to UndoStack
     */
    suspend fun onEvent(value: String) {
        undoStack.add(value)
        updateState()
    }


    /**
     * When pressed undo, 1 event will be popped and then pushed into RedoStack
     */
    suspend fun onUndoClicked() {

    }

    /**
     * When pressed redo, 1 event will be popped and then pushed back into UndoStack
     */
    fun onRedoClicked() {

    }


    /**
     * Recalculate and update visibility state of the buttons
     */
    private suspend fun updateState() {
        _undoRedoVisibilityFlow.emit(
            UndoRedoButtonState(
                undoStack.isNotEmpty(),
                undoStack.isNotEmpty() && redoStack.isNotEmpty()
            )
        )
    }

}
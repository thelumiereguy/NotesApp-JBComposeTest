/*
 * Created by Piyush Pradeepkumar on 18/04/21, 8:40 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.presentation.viewmodel.update_note

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class UndoRedoHandler(
    private val onContentChangedCallBack: OnContentChangedCallBack
) {

    private val undoStack = Stack<String>()
    private val redoStack = Stack<String>()
    private val contentHistory = Stack<String>()

    private val _undoRedoVisibilityFlow = Channel<UndoRedoButtonState>()
    val undoRedoVisibilityFlow: Flow<UndoRedoButtonState> = _undoRedoVisibilityFlow.receiveAsFlow()

    private val events: Channel<Pair<String, String>> = Channel(1)
    private val eventsFlow = events.consumeAsFlow()

    init {
        GlobalScope.launch {
            eventsFlow.debounce(200L).distinctUntilChanged().collect { (oldValue, currentValue) ->
                undoStack.push(oldValue)
                contentHistory.push(currentValue)
                updateState()
            }
        }
    }

    /**
     * Events from Keyboard are added to UndoStack
     */
    fun onEvent(value: String, newContent: String) {
        events.offer(Pair(value, newContent))
    }


    /**
     * When pressed undo, 1 event will be popped and then pushed into RedoStack
     */
    suspend fun onUndoClicked() {
        if (undoStack.isNotEmpty()) {
            val previousContent = undoStack.pop()
            onContentChangedCallBack.onContentUpdated(previousContent)
        }
        updateState()
    }

    /**
     * When pressed redo, 1 event will be popped and then pushed back into UndoStack
     */
    suspend fun onRedoClicked() {
        if (redoStack.isNotEmpty()) {
            val updatedContent = contentHistory.pop()
            onContentChangedCallBack.onContentUpdated(updatedContent)
        }
        updateState()
    }


    /**
     * Recalculate and update visibility state of the buttons
     */
    private suspend fun updateState() {
        _undoRedoVisibilityFlow.send(
            UndoRedoButtonState(
                undoStack.isNotEmpty(),
                undoStack.isNotEmpty() || redoStack.isNotEmpty()
            )
        )
    }

}

interface OnContentChangedCallBack {
    fun onContentUpdated(newContent: String)
}
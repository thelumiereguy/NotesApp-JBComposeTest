/*
 * Created by Piyush Pradeepkumar on 18/04/21, 10:29 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.presentation.viewmodel.update_note

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import me.user.common.notes.data.NotesRepository
import me.user.common.notes.data.models.Note
import me.user.common.runTest
import kotlin.test.*

@ExperimentalCoroutinesApi
internal class UpdateNoteViewModelTest {

    private var mockNotesRepository: NotesRepository = mockk()

    private lateinit var updateNoteViewModel: UpdateNoteViewModel

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)

        updateNoteViewModel = UpdateNoteViewModel(mockNotesRepository, UndoRedoHandler())
    }


    @Test
    fun `initial State should be loading`() {
        assertEquals(States.Loading, updateNoteViewModel.screenState.value)
    }

    @Test
    fun `when note content is empty, button state should be disabled`() = runTest {

        val note = Note("", "", "", System.currentTimeMillis(), 0)

        // Return Note with empty title and body
        coEvery {
            mockNotesRepository.findNoteById(0)
        } returns flowOf(note)

        updateNoteViewModel.getNoteById(0)

        assertEquals(States.ShowNote(note), updateNoteViewModel.screenState.value)

        assertFalse(updateNoteViewModel.saveButtonState.first().enabled)
    }

    @Test
    fun `when note's title and content are not empty, button should be enabled`() = runTest {

        val note = Note("title", "content", "", System.currentTimeMillis(), 0)

        // Return Note with empty title and body
        coEvery {
            mockNotesRepository.findNoteById(0)
        } returns flowOf(note)

        updateNoteViewModel.getNoteById(0)

        assertEquals(States.ShowNote(note), updateNoteViewModel.screenState.value)

        assert(updateNoteViewModel.saveButtonState.first().enabled)
    }

    @Test
    fun `when note's content changes, undo button should be enabled`() = runTest {

        val note = Note("title", "content", "", System.currentTimeMillis(), 0)

        // Return Note with empty title and body
        coEvery {
            mockNotesRepository.findNoteById(0)
        } returns flowOf(note)

        updateNoteViewModel.getNoteById(0)

        GlobalScope.launch {
            updateNoteViewModel.observeUndoRedoState()
        }

        updateNoteViewModel.onContentChanged("newContent")

        assertTrue(updateNoteViewModel.undoRedoButtonState.first().undoEnabled)
        assertFalse(updateNoteViewModel.undoRedoButtonState.first().redoEnabled)
    }

    @Test
    fun `when clicking undo, the note content should be reset`() = runTest {


        val note = Note("title", "content", "", System.currentTimeMillis(), 0)

        // Return Note with empty title and body
        coEvery {
            mockNotesRepository.findNoteById(0)
        } returns flowOf(note)

        updateNoteViewModel.getNoteById(0)

        GlobalScope.launch {
            updateNoteViewModel.observeUndoRedoState()

            updateNoteViewModel.onContentChanged("newContent")

            updateNoteViewModel.undoClicked()

            assertEquals("content", updateNoteViewModel.contentTextState.value)

            assertFalse(updateNoteViewModel.undoRedoButtonState.first().undoEnabled)

            assertTrue(updateNoteViewModel.undoRedoButtonState.first().redoEnabled)
        }
    }


    @Test
    fun `when clicking redo, the note content should be updated`() = runTest {


        val note = Note("title", "content", "", System.currentTimeMillis(), 0)

        // Return Note with empty title and body
        coEvery {
            mockNotesRepository.findNoteById(0)
        } returns flowOf(note)

        updateNoteViewModel.getNoteById(0)

        GlobalScope.launch {
            updateNoteViewModel.observeUndoRedoState()

            updateNoteViewModel.onContentChanged("newContent")

            updateNoteViewModel.undoClicked()

            assertEquals("content", updateNoteViewModel.contentTextState.value)

            updateNoteViewModel.redoClicked()

            assertEquals("newContent", updateNoteViewModel.contentTextState.value)

            assertFalse(updateNoteViewModel.undoRedoButtonState.first().undoEnabled)

            assertFalse(updateNoteViewModel.undoRedoButtonState.first().redoEnabled)
        }
    }
}
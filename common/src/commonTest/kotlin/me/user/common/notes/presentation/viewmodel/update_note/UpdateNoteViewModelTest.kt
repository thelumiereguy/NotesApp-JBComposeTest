/*
 * Created by Piyush Pradeepkumar on 18/04/21, 10:29 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.presentation.viewmodel.update_note

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import me.user.common.notes.data.NotesRepository
import me.user.common.notes.data.models.Note
import me.user.common.runTest
import kotlin.test.*
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
@ExperimentalCoroutinesApi
internal class UpdateNoteViewModelTest {

    private var mockNotesRepository: NotesRepository = mockk()

    private lateinit var updateNoteViewModel: UpdateNoteViewModel

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)

        updateNoteViewModel = UpdateNoteViewModel(mockNotesRepository)
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

        updateNoteViewModel.undoRedoButtonState.test(1.seconds) {

            updateNoteViewModel.onContentChanged("newContent")

            val state = expectItem()
            print(state)
            assert(state.undoEnabled)
            assertFalse(state.redoEnabled)
            cancel()
        }
    }

    @Test
    fun `when clicking undo, the note content should be reset`() = runTest {


        val note = Note("title", "content", "", System.currentTimeMillis(), 0)

        // Return Note with empty title and body
        coEvery {
            mockNotesRepository.findNoteById(0)
        } returns flowOf(note)

        updateNoteViewModel.getNoteById(0)


        updateNoteViewModel.undoRedoButtonState.test(1.seconds) {

            updateNoteViewModel.onContentChanged("newContent")

            expectItem()

            updateNoteViewModel.undoClicked()

            val state = expectItem()

            print(state)
            assertEquals("content", updateNoteViewModel.contentTextState.value)

            assertFalse(state.undoEnabled)

            assertTrue(state.redoEnabled)
            cancel()
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


        updateNoteViewModel.undoRedoButtonState.test(1.seconds) {

            updateNoteViewModel.onContentChanged("newContent")

            expectItem()

            updateNoteViewModel.undoClicked()

            expectItem()

            assertEquals("content", updateNoteViewModel.contentTextState.value)

            updateNoteViewModel.redoClicked()

            val state = expectItem()

            print(state)

            assertEquals("newContent", updateNoteViewModel.contentTextState.value)

            assertFalse(state.undoEnabled)

            assertFalse(state.redoEnabled)

            cancel()
        }
    }
}
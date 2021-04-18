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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import me.user.common.notes.data.NotesRepository
import me.user.common.notes.data.models.Note
import me.user.common.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.core.Is
import org.junit.Assert
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
        Assert.assertThat(updateNoteViewModel.screenState.value, Is(equalTo(States.Loading)))
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

        updateNoteViewModel.onContentChanged("content")

        assertTrue(updateNoteViewModel.undoRedoButtonState.first().undoEnabled)
    }
}
package com.ernestschneider.feature.remindercereration.notecreation

import com.ernestschcneider.feature.remindercreation.notecreation.NoteCreationViewModel
import com.ernestschcneider.remindersapp.core.dispatchers.CoroutineTestExtension
import com.ernestschneider.testutils.InMemoryLocalRepo
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class NoteCreationViewModelTest {
    private val localRepo = InMemoryLocalRepo()
    private val backgroundDispatcher = Dispatchers.Unconfined
    private val viewModel = NoteCreationViewModel(
        localRepo = localRepo,
        backgroundDispatcher= backgroundDispatcher
    )

    @Test
    fun onNoteContentUpdate() {
        val reminderContent = "reminderContent"

        viewModel.onNoteContentUpdate(reminderContent)

        assertEquals(reminderContent, viewModel.screenState.value.noteContent)
    }
    
    @Test
    fun onNoteTitleUpdate() {
        val reminderTitle = "reminderTitle"

        viewModel.onNoteTitleUpdate(reminderTitle)

        assertEquals(reminderTitle, viewModel.screenState.value.noteTitle)
    }
    
    @Test
    fun onSavedNoteClickedNotEmptyTitle() {
        val noteTitle = "noteTitle"
        val noteContent = "noteContent"
        viewModel.onNoteTitleUpdate(noteTitle)
        viewModel.onNoteContentUpdate(noteContent)

        viewModel.onSavedNoteClicked()

        assertEquals(noteTitle, viewModel.screenState.value.noteTitle)
        assertEquals(noteContent, viewModel.screenState.value.noteContent)
        assertEquals(true, viewModel.screenState.value.backNavigation)
    }

    @Test
    fun onSavedNoteClickedEmptyContent() {
        val noteTitle = "noteTitle"
        val noteContent = ""
        val backNavigation = true
        viewModel.onNoteTitleUpdate(noteTitle)

        viewModel.onSavedNoteClicked()

        assertEquals(noteTitle, viewModel.screenState.value.noteTitle)
        assertEquals(noteContent, viewModel.screenState.value.noteContent)
        assertEquals(backNavigation, viewModel.screenState.value.backNavigation)
    }

    @Test
    fun onSavedNoteClickedEmptyTitle() {
        val noteContent = "noteContent"
        viewModel.onNoteContentUpdate(noteContent)
        val showEmptyTitleDialog = true
        val backNavigation = false

        viewModel.onSavedNoteClicked()

        assertEquals(noteContent, viewModel.screenState.value.noteContent)
        assertEquals(backNavigation, viewModel.screenState.value.backNavigation)
        assertEquals(showEmptyTitleDialog, viewModel.screenState.value.showEmptyTitleDialog)
    }
}
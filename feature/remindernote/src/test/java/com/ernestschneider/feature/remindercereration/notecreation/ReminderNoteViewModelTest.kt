package com.ernestschneider.feature.remindercereration.notecreation

import androidx.lifecycle.SavedStateHandle
import com.ernestschcneider.EMPTY_REMINDER_ID
import com.ernestschcneider.feature.remindernote.REMINDER_ID_ARG
import com.ernestschcneider.feature.remindernote.ReminderNoteViewModel
import com.ernestschcneider.remindersapp.core.dispatchers.CoroutineTestExtension
import com.ernestschneider.testutils.InMemoryLocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class ReminderNoteViewModelTest {
    private val localRepo = InMemoryLocalRepo()
    private val backgroundDispatcher = Dispatchers.Unconfined
    private val savedStateHandle = getSavedStateHandle()

    private val viewModel = ReminderNoteViewModel(
        localRepo = localRepo,
        backgroundDispatcher= backgroundDispatcher,
        savedStateHandle = savedStateHandle
    )

    @Test
    fun onNoteContentUpdate() {
        val reminderContent = "reminderContent"

        viewModel.onNoteContentUpdate(reminderContent)

        assertEquals(reminderContent, viewModel.screenState.value.reminderContent)
    }
    
    @Test
    fun onNoteTitleUpdate() {
        val reminderTitle = "reminderTitle"

        viewModel.onNoteTitleUpdate(reminderTitle)

        assertEquals(reminderTitle, viewModel.screenState.value.reminderTitle)
    }
    
    @Test
    fun onSavedNoteClickedNotEmptyTitle() {
        val noteTitle = "noteTitle"
        val noteContent = "noteContent"
        viewModel.onNoteTitleUpdate(noteTitle)
        viewModel.onNoteContentUpdate(noteContent)

        viewModel.onSavedNoteClicked()

        assertEquals(noteTitle, viewModel.screenState.value.reminderTitle)
        assertEquals(noteContent, viewModel.screenState.value.reminderContent)
        assertEquals(true, viewModel.screenState.value.backNavigation)
    }

    @Test
    fun onSavedNoteClickedEmptyContent() {
        val noteTitle = "noteTitle"
        val noteContent = ""
        val backNavigation = true
        viewModel.onNoteTitleUpdate(noteTitle)

        viewModel.onSavedNoteClicked()

        assertEquals(noteTitle, viewModel.screenState.value.reminderTitle)
        assertEquals(noteContent, viewModel.screenState.value.reminderContent)
        assertEquals(backNavigation, viewModel.screenState.value.backNavigation)
    }

    @Test
    fun onSavedNoteClickedEmptyTitle() {
        val noteContent = "noteContent"
        viewModel.onNoteContentUpdate(noteContent)
        val showEmptyTitleDialog = true
        val backNavigation = false

        viewModel.onSavedNoteClicked()

        assertEquals(noteContent, viewModel.screenState.value.reminderContent)
        assertEquals(backNavigation, viewModel.screenState.value.backNavigation)
        assertEquals(showEmptyTitleDialog, viewModel.screenState.value.showEmptyTitleDialog)
    }

    @Test
    fun onLoadNoteReminderEmptyReminderId() {
        val requestFocus = true

        viewModel.loadNoteReminder()

        assertEquals(requestFocus, viewModel.screenState.value.requestFocus)
    }

    @Test
    fun onLoadNoteReminderNotEmptyReminderId() = runTest {
        val reminderId = "1"
        // TODO improve this
        val viewModel2 = ReminderNoteViewModel(
            savedStateHandle = getSavedStateHandle(reminderId = reminderId),
            localRepo = localRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        val reminder = localRepo.getReminder(reminderId)

        viewModel2.loadNoteReminder()

        assertEquals(reminder.reminderTitle, viewModel2.screenState.value.reminderTitle)
        assertEquals(reminder.reminderContent, viewModel2.screenState.value.reminderContent)
        assertEquals(false, viewModel2.screenState.value.showSaveButton)
    }

    @Test
    fun onDismissEmptyTitleDialog() {
        viewModel.onSavedNoteClicked()

        viewModel.onDismissEmptyTitleDialog()

        assertEquals(false, viewModel.screenState.value.showEmptyTitleDialog )
    }

    private fun getSavedStateHandle(reminderId: String = EMPTY_REMINDER_ID): SavedStateHandle {
        return SavedStateHandle().apply {
            set(REMINDER_ID_ARG, reminderId)
        }
    }
}
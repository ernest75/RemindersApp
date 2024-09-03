package com.ernestschneider.feature.remindercereration.notecreation

import androidx.lifecycle.SavedStateHandle
import com.ernestschcneider.EMPTY_REMINDER_ID
import com.ernestschcneider.feature.remindernote.REMINDER_ID_ARG
import com.ernestschcneider.feature.remindernote.ReminderNoteViewModel
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.core.dispatchers.CoroutineTestExtension
import com.ernestschneider.testutils.InMemoryLocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify

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
    fun onReminderContentUpdate() {
        val reminderContent = "reminderContent"

        viewModel.onReminderContentUpdate(reminderContent)

        assertEquals(reminderContent, viewModel.screenState.value.reminderContent)
        assertEquals(true, viewModel.screenState.value.showSaveButton)
        assertEquals(false, viewModel.screenState.value.requestFocus)
    }
    
    @Test
    fun onReminderTitleUpdate() {
        val reminderTitle = "reminderTitle"

        viewModel.onReminderTitleUpdate(reminderTitle)

        assertEquals(reminderTitle, viewModel.screenState.value.reminderTitle)
        assertEquals(true, viewModel.screenState.value.showSaveButton)
        assertEquals(false, viewModel.screenState.value.requestFocus)
    }
    
    @Test
    fun onSavedReminderClickedNotEmptyTitle() {
        val reminderTitle = "reminderTitle"
        val reminderContent = "reminderContent"
        viewModel.onReminderTitleUpdate(reminderTitle)
        viewModel.onReminderContentUpdate(reminderContent)

        viewModel.onSavedReminderClicked()

        assertEquals(reminderTitle, viewModel.screenState.value.reminderTitle)
        assertEquals(reminderContent, viewModel.screenState.value.reminderContent)
        assertEquals(true, viewModel.screenState.value.backNavigation)
    }

    @Test
    fun onSavedReminderClickedEmptyContent() {
        val reminderTitle = "noteTitle"
        val reminderContent = ""
        val backNavigation = true
        viewModel.onReminderTitleUpdate(reminderTitle)

        viewModel.onSavedReminderClicked()

        assertEquals(reminderTitle, viewModel.screenState.value.reminderTitle)
        assertEquals(reminderContent, viewModel.screenState.value.reminderContent)
        assertEquals(backNavigation, viewModel.screenState.value.backNavigation)
    }

    @Test
    fun onSavedExistingReminderClicked() = runTest {
        val reminderId = "3"
        val reminderTitle = "noteTitle"
        val reminderContent = "foo"
        val backNavigation = true
        val spiedLocalRepo = spy(localRepo)
        // TODO improve this?
        val viewModel = ReminderNoteViewModel(
            savedStateHandle = getSavedStateHandle(reminderId = reminderId),
            localRepo = spiedLocalRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        viewModel.onReminderTitleUpdate(reminderTitle)
        viewModel.onReminderContentUpdate(reminderContent)
        val reminder = Reminder(
            id = reminderId,
            reminderTitle = reminderTitle,
            reminderContent = reminderContent
        )

        viewModel.onSavedReminderClicked()

        assertEquals(backNavigation, viewModel.screenState.value.backNavigation)
        val savedReminder = localRepo.getReminder(reminderId)
        assertEquals(reminder, savedReminder)
        verify(spiedLocalRepo).updateReminder(reminder)
    }

    @Test
    fun onSavedReminderClickedEmptyTitle() {
        val reminderContent = "reminderContent"
        viewModel.onReminderContentUpdate(reminderContent)
        val showEmptyTitleDialog = true
        val backNavigation = false

        viewModel.onSavedReminderClicked()

        assertEquals(reminderContent, viewModel.screenState.value.reminderContent)
        assertEquals(backNavigation, viewModel.screenState.value.backNavigation)
        assertEquals(showEmptyTitleDialog, viewModel.screenState.value.showEmptyTitleDialog)
    }

    @Test
    fun onLoadReminderEmptyReminderId() {
        val requestFocus = true

        viewModel.loadReminder()

        assertEquals(requestFocus, viewModel.screenState.value.requestFocus)
    }

    @Test
    fun onLoadReminderNotEmptyReminderId() = runTest {
        val reminderId = "3"
        // TODO improve this?
        val viewModel = ReminderNoteViewModel(
            savedStateHandle = getSavedStateHandle(reminderId = reminderId),
            localRepo = localRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        val reminder = localRepo.getReminder(reminderId)

        viewModel.loadReminder()

        assertEquals(reminder.reminderTitle, viewModel.screenState.value.reminderTitle)
        assertEquals(reminder.reminderContent, viewModel.screenState.value.reminderContent)
        assertEquals(reminder.reminderType, ReminderType.Note)
        assertEquals(false, viewModel.screenState.value.showSaveButton)
    }

    @Test
    fun onDismissEmptyTitleDialog() {
        viewModel.onSavedReminderClicked()

        viewModel.onDismissEmptyTitleDialog()

        assertEquals(false, viewModel.screenState.value.showEmptyTitleDialog )
    }

    private fun getSavedStateHandle(reminderId: String = EMPTY_REMINDER_ID): SavedStateHandle {
        return SavedStateHandle().apply {
            set(REMINDER_ID_ARG, reminderId)
        }
    }
}

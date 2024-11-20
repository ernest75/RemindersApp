package com.ernestschneider.feature.remindercereration.notecreation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.ernestschcneider.EMPTY_REMINDER_ID
import com.ernestschcneider.feature.remindernote.REMINDER_ID_ARG
import com.ernestschcneider.feature.remindernote.ReminderNoteViewModel
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.core.dispatchers.CoroutineTestExtension
import com.ernestschneider.testutils.InMemoryLocalRepo
import com.ernestschneider.testutils.ReminderBuilder
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
        backgroundDispatcher = backgroundDispatcher,
        savedStateHandle = savedStateHandle
    )

    private val reminderNote1 = ReminderBuilder.aReminder()
        .withId("1")
        .withReminderTitle("Title1")
        .withReminderContent("Content1")
        .withReminderType(ReminderType.Note)
        .build()

    private val reminderList1 = ReminderBuilder.aReminder().withId("2").withReminderTitle("Title2")
        .withReminderType(ReminderType.List).withReminderList(
            arrayListOf(
                ReminderListItem(position = 0, text = "Element1"),
                ReminderListItem(position = 1, text = "Element2")
            )
        ).build()

    @Test
    fun onReminderContentUpdate() {
        val reminderContent = "reminderContent"
        val textFieldValue = TextFieldValue(reminderContent)

        viewModel.onReminderContentUpdate(textFieldValue)

        assertEquals(reminderContent, viewModel.screenState.value.reminderContent)
        assertEquals(true, viewModel.screenState.value.showSaveButton)
        assertEquals(false, viewModel.screenState.value.requestFocus)
    }

    @Test
    fun onReminderTitleUpdate() {
        val reminderTitle = "reminderTitle"
        viewModel.onReminderTitleUpdate(reminderTitle)
        viewModel.onSavedReminderClicked()

        viewModel.onReminderTitleUpdate(reminderTitle)

        assertEquals(reminderTitle, viewModel.screenState.value.reminderTitle)
        assertEquals(true, viewModel.screenState.value.showSaveButton)
        assertEquals(false, viewModel.screenState.value.requestFocus)
    }

    @Test
    fun onReminderTitleUpdateSameTitle() = runTest {
        val reminderId = "1"
        val viewModel = ReminderNoteViewModel(
            savedStateHandle = getSavedStateHandle(reminderId = reminderId),
            localRepo = localRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        localRepo.saveReminders(listOf(reminderNote1, reminderList1))
        val reminder = localRepo.getReminder(reminderId)
        viewModel.loadReminder()

        viewModel.onReminderTitleUpdate(reminder.reminderTitle)

        assertEquals(reminder.reminderTitle, viewModel.screenState.value.reminderTitle)
        assertEquals(false, viewModel.screenState.value.showSaveButton)
    }

    @Test
    fun onSavedReminderClickedNotEmptyTitle() {
        val reminderTitle = "reminderTitle"
        val reminderContent = "reminderContent"
        val textFieldValue = TextFieldValue(reminderContent)
        viewModel.onReminderTitleUpdate(reminderTitle)
        viewModel.onReminderContentUpdate(textFieldValue)

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
        val reminderId = "1"
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
        localRepo.saveReminders(listOf(reminderNote1, reminderList1))
        viewModel.onReminderTitleUpdate(reminderTitle)
        val textFieldValue = TextFieldValue(reminderContent)
        viewModel.onReminderContentUpdate(textFieldValue)
        val reminder = Reminder(
            reminderId = reminderNote1.reminderId,
            reminderTitle = reminderTitle,
            reminderContent = reminderContent,
            remindersList = reminderNote1.remindersList,
            reminderPosition = reminderNote1.reminderPosition,
            reminderType = reminderNote1.reminderType
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
        val textFieldValue = TextFieldValue(reminderContent)
        viewModel.onReminderContentUpdate(textFieldValue)
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
        val reminderId = "1"
        val viewModel = ReminderNoteViewModel(
            savedStateHandle = getSavedStateHandle(reminderId = reminderId),
            localRepo = localRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        localRepo.saveReminders(listOf(reminderNote1, reminderList1))
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

        assertEquals(false, viewModel.screenState.value.showEmptyTitleDialog)
    }

    private fun getSavedStateHandle(reminderId: String = EMPTY_REMINDER_ID): SavedStateHandle {
        return SavedStateHandle().apply {
            set(REMINDER_ID_ARG, reminderId)
        }
    }
}

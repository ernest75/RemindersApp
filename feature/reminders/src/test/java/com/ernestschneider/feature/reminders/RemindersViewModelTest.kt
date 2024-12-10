package com.ernestschneider.feature.reminders

import com.ernestschcneider.feature.reminders.RemindersViewModel
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.core.dispatchers.CoroutineTestExtension
import com.ernestschneider.testutils.InMemoryLocalRepo
import com.ernestschneider.testutils.ReminderBuilder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class RemindersViewModelTest {

    private val localRepo = InMemoryLocalRepo()
    private val backgroundDispatcher = Dispatchers.Unconfined
    private val viewModel = RemindersViewModel(
        localRepo = localRepo,
        backgroundDispatcher = backgroundDispatcher
    )

    private val reminderNote1 = ReminderBuilder.aReminder()
        .withId("1")
        .withReminderTitle("Title1")
        .withReminderContent("Content1")
        .withReminderPosition(0)
        .withReminderType(ReminderType.Note)
        .build()

    private val reminderList1 = ReminderBuilder.aReminder()
        .withId("2")
        .withReminderTitle("Title2")
        .withReminderType(ReminderType.List)
        .withReminderPosition(1)
        .withReminderList(
            arrayListOf(
                ReminderListItem(position = 0, text = "Element1"),
                ReminderListItem(position = 1, text = "Element2")
            )
        ).build()

    @Test
    fun onInitialDefaultState() {
        assertEquals(emptyList<Reminder>(), viewModel.screenState.value.reminders)
        assertEquals(false, viewModel.screenState.value.showCreationDialog)
    }

    @Test
    fun loadingReminders() {
        val reminders = localRepo.getReminders()

        viewModel.loadReminders()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(reminders = reminders, showLoading = false))
    }

    @Test
    fun removeItem() {
        localRepo.saveReminders(listOf(reminderNote1, reminderList1))
        val reminder = localRepo.getReminderAt(1)

        viewModel.removeItem(reminder)

        assertEquals(localRepo.getReminders(), viewModel.screenState.value.reminders)
    }

    @Test
    fun onAddButtonClicked() {
        viewModel.onAddButtonClicked()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(showCreationDialog = true))
    }

    @Test
    fun onDismissDialog() {
        viewModel.onDismissDialog()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(showCreationDialog = false))
    }

    @Test
    fun onOnMoveReminderSamePosition() {
        val list = localRepo.getReminders()
        viewModel.loadReminders()

        viewModel.onMoveReminder(0, 0)

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(reminders = list))
    }

    @Test
    fun onOnMoveReminderDifferentPosition() {
        localRepo.saveReminders(listOf(reminderNote1, reminderList1))
        val list = localRepo.getReminders()
        val from = 0
        val to = 1
        val listModified = list.toMutableList().apply {
            val element = this.removeAt(from)
            this.add(to, element)
        }
        viewModel.loadReminders()

        viewModel.onMoveReminder(from, to)

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(reminders = listModified))
    }

    @Test
    fun onReminderMoved() = runTest {
        val reminderPosition1 = 0
        val reminderPosition2 = 1
        val reminder1 = ReminderBuilder
            .aReminder()
            .withReminderPosition(reminderPosition1)
            .withId("1")
            .build()
        val reminder2 = ReminderBuilder
            .aReminder()
            .withReminderPosition(reminderPosition2)
            .withId("2")
            .build()
        val reminder1AfterMoving = ReminderBuilder
            .aReminder()
            .withReminderPosition(reminderPosition2)
            .withId("1")
            .build()
        val reminder2AfterMoving = ReminderBuilder
            .aReminder()
            .withReminderPosition(reminderPosition1)
            .withId("2")
            .build()
        val listB4Moving = listOf(reminder1, reminder2)
        val lisAfterMoving = listOf(reminder2AfterMoving, reminder1AfterMoving)
        localRepo.saveReminders(listB4Moving)
        viewModel.loadReminders()
        viewModel.onMoveReminder(reminderPosition2, reminderPosition1)

        viewModel.onReminderMoved()

        val list = localRepo.getReminders()
        assertEquals(lisAfterMoving, list)
    }

    @Test
    fun onShowLoading() {
        val showLoading = true

        viewModel.showLoading()

        assertEquals(showLoading, viewModel.screenState.value.showLoading)
    }
}

package com.ernestschneider.feature.reminders

import com.ernestschcneider.feature.reminders.RemindersViewModel
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.remindersapp.core.dispatchers.CoroutineTestExtension
import com.ernestschneider.testutils.InMemoryLocalRepo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
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

    @Test
    fun onInitialDefaultState() {
        assertEquals(emptyList<Reminder>(), viewModel.screenState.value.reminders)
        assertEquals(false, viewModel.screenState.value.showCreationDialog)
    }

    @Test
    fun loadingReminders() {
        viewModel.loadReminders()

        assertEquals(localRepo.getReminders(), viewModel.screenState.value.reminders)
    }

    @Test
    fun removeItem() {
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
}

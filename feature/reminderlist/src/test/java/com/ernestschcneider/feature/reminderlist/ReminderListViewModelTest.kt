package com.ernestschcneider.feature.reminderlist

import androidx.lifecycle.SavedStateHandle
import com.ernestschcneider.EMPTY_REMINDER_ID
import com.ernestschcneider.remindersapp.core.dispatchers.CoroutineTestExtension
import com.ernestschneider.testutils.InMemoryLocalRepo
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class ReminderListViewModelTest { private val localRepo = InMemoryLocalRepo()
    private val backgroundDispatcher = Dispatchers.Unconfined
    private val savedStateHandle = getSavedStateHandle()

    private val viewModel = ReminderListViewModel(
        localRepo = localRepo,
        backgroundDispatcher = backgroundDispatcher,
        savedStateHandle = savedStateHandle
    )

    @Test
    fun onReminderListTitleUpdate() {
        val reminderListTitle = "reminderListTitle"

        viewModel.onReminderListTitleUpdate(reminderListTitle)

        Assertions.assertEquals(reminderListTitle, viewModel.screenState.value.reminderListTitle)
        Assertions.assertTrue(viewModel.screenState.value.showSaveButton)
    }

    @Test
    fun onFirstReminderListClicked() {
        viewModel.onFirstReminderListClicked()

        Assertions.assertTrue(viewModel.screenState.value.showCreateReminderDialog)
    }

    @Test
    fun onDismissDialogClicked() {
        // to force showing dialog on screen
        viewModel.onFirstReminderListClicked()

        viewModel.onDismissDialogClicked()

        Assertions.assertFalse(viewModel.screenState.value.showCreateReminderDialog)
    }

    private fun getSavedStateHandle(reminderListId: String = EMPTY_REMINDER_ID): SavedStateHandle {
        return SavedStateHandle().apply {
            set(REMINDER_LIST_ID_ARG, reminderListId)
        }
    }
}

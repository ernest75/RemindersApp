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
    fun onAddFirstReminderListClicked() {
        viewModel.onAddFirstReminderListClicked()

        Assertions.assertTrue(viewModel.screenState.value.showCreateReminderDialog)
        Assertions.assertTrue(viewModel.screenState.value.isFirstReminder)
    }

    @Test
    fun onDismissCreateDialogClicked() {
        // to force showing dialog on screen
        viewModel.onAddFirstReminderListClicked()

        viewModel.onDismissCreateDialogClicked()

        Assertions.assertFalse(viewModel.screenState.value.showCreateReminderDialog)
    }
    
    @Test
    fun onFirstReminderListItemAdded() {
        val reminderText = "reminderText"
        val reminderText2 = "reminderText2"
        val reminderList = listOf(reminderText2, reminderText)
        viewModel.onFirstReminderListItemAdded(reminderText)

        viewModel.onFirstReminderListItemAdded(reminderText2)
        Assertions.assertEquals(reminderList,viewModel.screenState.value.remindersList)


        Assertions.assertTrue(viewModel.screenState.value.showSaveButton)
    }

    @Test
    fun onAddLastReminderClicked() {
        viewModel.onAddLastReminderListClicked()

        Assertions.assertTrue(viewModel.screenState.value.showCreateReminderDialog)
        Assertions.assertFalse(viewModel.screenState.value.isFirstReminder)
    }

    @Test
    fun onLastReminderListItemAdded() {
        val reminderText = "reminderText"
        val reminderText2 = "reminderText2"
        val reminderList = listOf(reminderText, reminderText2)
        viewModel.onLastReminderListItemAdded(reminderText)

        viewModel.onLastReminderListItemAdded(reminderText2)

        Assertions.assertEquals(reminderList,viewModel.screenState.value.remindersList)
    }

    @Test
    fun onSaveReminderListNotEmptyTitle() {
        val reminderListTitle = "title"
        val firstReminder = "1"
        val lastReminder = "2"
        val reminderList = listOf(firstReminder, lastReminder)
        val backNavigation = true
        viewModel.onFirstReminderListItemAdded(firstReminder)
        viewModel.onLastReminderListItemAdded(lastReminder)
        viewModel.onReminderListTitleUpdate(reminderListTitle)

        viewModel.onSaveListReminderClicked()

        Assertions.assertEquals(reminderListTitle, viewModel.screenState.value.reminderListTitle)
        Assertions.assertEquals(reminderList, viewModel.screenState.value.remindersList)
        Assertions.assertTrue(backNavigation)
    }

    @Test
    fun onSaveReminderListEmptyTitle() {
        val firstReminder = "1"
        val lastReminder = "2"
        viewModel.onFirstReminderListItemAdded(firstReminder)
        viewModel.onLastReminderListItemAdded(lastReminder)

        viewModel.onSaveListReminderClicked()

        Assertions.assertTrue(viewModel.screenState.value.showEmptyTitleDialog)
    }

    @Test
    fun onDismissEmptyTitleDialogClicked() {
        viewModel.onSaveListReminderClicked()

        viewModel.onDismissEmptyTitleDialogClicked()

        Assertions.assertFalse(viewModel.screenState.value.showEmptyTitleDialog)
    }

    private fun getSavedStateHandle(reminderListId: String = EMPTY_REMINDER_ID): SavedStateHandle {
        return SavedStateHandle().apply {
            set(REMINDER_LIST_ID_ARG, reminderListId)
        }
    }
}

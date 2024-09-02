package com.ernestschcneider.feature.reminderlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ernestschcneider.remindersapp.local.StorageRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReminderListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localRepo: StorageRepo,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _screenState = MutableStateFlow(ReminderListState())
    val screenState: StateFlow<ReminderListState> = _screenState.asStateFlow()
    private val reminderNoteArgs = ReminderListArgs(savedStateHandle)

    fun onReminderListTitleUpdate(reminderListTitle: String) {
        _screenState.update {
            it.copy(
                reminderListTitle = reminderListTitle,
                showSaveButton = true
            )
        }
    }

    fun onAddFirstReminderListClicked() {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = true,
                requestFocus = true,
                isFirstReminder = true
            )
        }
    }

    fun onFirstReminderListItemAdded(reminderText: String) {
        val firstIndex = 0
        _screenState.value.remindersList.apply {
            add(firstIndex, reminderText) }
        _screenState.update {
            it.copy(
                remindersList = _screenState.value.remindersList,
                showSaveButton = true
            )
        }
    }

    fun onDismissDialogClicked() {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = false
            )
        }
    }

    fun onAddLastReminderListClicked() {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = true,
                requestFocus = true,
                isFirstReminder = false
            )
        }
    }

    fun onLastReminderListItemAdded(reminderText: String) {
        _screenState.value.remindersList.apply {
            add(reminderText) }
        _screenState.update {
            it.copy(
                remindersList = _screenState.value.remindersList,
                showSaveButton = true
            )
        }
    }
}

package com.ernestschcneider.feature.reminderlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.local.StorageRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                remindersList = _screenState.value.remindersList
            )
        }
    }

    fun onDismissCreateDialogClicked() {
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
            )
        }
    }

    fun onSaveListReminderClicked() {
        if (_screenState.value.reminderListTitle.isNotEmpty()) {
            val remindersArray = arrayListOf<String>().apply {
                addAll(_screenState.value.remindersList)
            }
            val reminder = Reminder(
                reminderTitle = _screenState.value.reminderListTitle,
                remindersList = remindersArray,
                reminderType = ReminderType.List
            )
            viewModelScope.launch {
                withContext(backgroundDispatcher) {
                    localRepo.saveReminder(reminder)
                    _screenState.update { it.copy(backNavigation = true) }
                }
            }
        } else {
            //TODO add dialog in screen
            _screenState.update { it.copy(showEmptyTitleDialog = true) }

        }
    }

    fun onDismissEmptyTitleDialogClicked() {
        _screenState.update {
            it.copy(
                showEmptyTitleDialog = false
            )
        }
    }
}

package com.ernestschcneider.feature.remindernote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.EMPTY_REMINDER_ID
import com.ernestschcneider.models.Reminder
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
class ReminderNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localRepo: StorageRepo,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _screenState = MutableStateFlow(ReminderNoteState())
    val screenState: StateFlow<ReminderNoteState> = _screenState.asStateFlow()
    private val reminderNoteArgs = ReminderNoteArgs(savedStateHandle)

    fun onReminderContentUpdate(reminderContent: String) {
        _screenState.update {
            it.copy(
                reminderContent = reminderContent,
                showSaveButton = true,
                requestFocus = false
            )
        }
    }

    fun onReminderTitleUpdate(reminderTitle: String) {
        _screenState.update {
            it.copy(
                reminderTitle = reminderTitle,
                showSaveButton = true,
                requestFocus = false
            )
        }
    }

    fun onSavedReminderClicked() {
        if (_screenState.value.reminderTitle.isNotEmpty()) {
            viewModelScope.launch {
                withContext(backgroundDispatcher) {
                    if (reminderNoteArgs.reminderId == EMPTY_REMINDER_ID) {
                        val reminder = Reminder(
                            reminderTitle = _screenState.value.reminderTitle,
                            reminderContent = _screenState.value.reminderContent
                        )
                        localRepo.saveReminder(reminder)
                    } else {
                        val reminderId = reminderNoteArgs.reminderId
                        val reminder = Reminder(
                            reminderId = reminderId,
                            reminderTitle = _screenState.value.reminderTitle,
                            reminderContent = _screenState.value.reminderContent
                        )
                        localRepo.updateReminder(reminder)
                    }
                    _screenState.update { it.copy(backNavigation = true) }
                }
            }
        } else {
            _screenState.update { it.copy(showEmptyTitleDialog = true) }

        }
    }

    fun loadReminder() {
        val reminderId = reminderNoteArgs.reminderId
        if (reminderId == EMPTY_REMINDER_ID) {
            _screenState.update { it.copy(requestFocus = true) }
        } else {
            viewModelScope.launch {
                withContext(backgroundDispatcher) {
                    val reminder = localRepo.getReminder(reminderId)
                    _screenState.update {
                        it.copy(
                            reminderTitle = reminder.reminderTitle,
                            reminderContent = reminder.reminderContent,
                            showSaveButton = false
                        )
                    }
                }
            }
        }
    }

    fun onDismissEmptyTitleDialog() {
        _screenState.update { it.copy(showEmptyTitleDialog = false) }
    }
}

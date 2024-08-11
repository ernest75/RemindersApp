package com.ernestschcneider.feature.remindernote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.feature.reminders.data.models.Reminder
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
    private val savedStateHandle: SavedStateHandle,
    private val localRepo: StorageRepo,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _screenState = MutableStateFlow(ReminderNoteState())
    val screenState: StateFlow<ReminderNoteState> = _screenState.asStateFlow()
    private val reminderNoteArgs = ReminderNoteArgs(savedStateHandle)

    fun onNoteContentUpdate(reminderContent: String) {
        _screenState.update { it.copy(reminderContent = reminderContent, showSaveButton = true) }
    }

    fun onNoteTitleUpdate(reminderTitle: String) {
        _screenState.update { it.copy(reminderTitle = reminderTitle, showSaveButton = true) }
    }

    fun onSavedNoteClicked() {
        if (_screenState.value.reminderTitle.isNotEmpty()) {
            val reminder = Reminder(
                reminderTitle = _screenState.value.reminderTitle,
                reminderContent = _screenState.value.reminderContent
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

    fun loadNoteReminder() {
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

    companion object {
        const val EMPTY_REMINDER_ID = " "
    }
}

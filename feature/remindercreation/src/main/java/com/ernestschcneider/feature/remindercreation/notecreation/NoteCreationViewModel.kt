package com.ernestschcneider.feature.remindercreation.notecreation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.feature.reminders.data.models.Reminder
import com.ernestschcneider.remindersapp.local.LocalRepo
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
class NoteCreationViewModel @Inject constructor(
    private val localRepo: StorageRepo,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _screenState = MutableStateFlow(ReminderCreationState())
    val screenState: StateFlow<ReminderCreationState> = _screenState.asStateFlow()

    fun onNoteContentUpdate(reminderContent: String) {
        _screenState.update { it.copy(noteContent = reminderContent) }
    }

    fun onNoteTitleUpdate(reminderTitle: String) {
        _screenState.update { it.copy(noteTitle = reminderTitle) }
    }

    fun onSavedNoteClicked() {
        if (_screenState.value.noteTitle.isNotEmpty()) {
            val reminder = Reminder(
                reminderTitle = _screenState.value.noteTitle,
                reminderContent = _screenState.value.noteContent
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
}

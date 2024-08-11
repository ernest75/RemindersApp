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
import javax.inject.Inject

@HiltViewModel
class NoteCreationViewModel @Inject constructor(
    private val localRepo: StorageRepo,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _screenState = MutableStateFlow(ReminderCreationState())
    val screenState: StateFlow<ReminderCreationState> = _screenState.asStateFlow()

    @VisibleForTesting
    private var reminderTitle: String? = null
    @VisibleForTesting
    private var reminderContent: String? = null

    fun onNoteContentUpdate(reminderContent: String) {
        this.reminderContent = reminderContent
        _screenState.update { it.copy(noteContent = reminderContent) }
    }

    fun onNoteTitleUpdate(reminderTitle: String) {
        this.reminderTitle = reminderTitle
    }

    fun onSavedNoteClicked() {
        // TODO add dialog informing is empty?
        if (reminderTitle.isNullOrEmpty().not()) {
            val reminder = Reminder(
                reminderTitle = reminderTitle.orEmpty(),
                reminderContent = reminderContent.orEmpty()
            )
            viewModelScope.launch {
                localRepo.saveReminder(reminder)
                _screenState.update { it.copy(backNavigation = true) }
            }
        } else {
            //TODO add dialog??
            println()
        }
    }
}

package com.ernestschcneider.feature.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.feature.reminders.data.models.ReminderType
import com.ernestschcneider.remindersapp.local.LocalRepo
import com.ernestschcneider.remindersapp.local.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel  @Inject constructor(
    private val localRepo: LocalRepo
) : ViewModel() {
    private val _screenState = MutableStateFlow(RemindersScreenState())
    val screenState: StateFlow<RemindersScreenState> = _screenState.asStateFlow()
    private var items = (1..10).map {
        Reminder(
            reminderTitle = "Reminder $it",
            reminderContent = "Content",
            reminderType = if (it % 3 == 0) {
                ReminderType.Note
            } else ReminderType.List
        )
    }

    fun loadReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            val reminders = localRepo.getAllReminders()
            _screenState.update { it.copy(reminders = reminders) }
        }
    }

    fun removeItem(item: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
           localRepo.deleteReminder(item)
           loadReminders()
        }
        _screenState.update { it.copy(reminders = items) }
    }

    fun onAddButtonClicked() {
        _screenState.update { it.copy(showCreationDialog = true) }
    }

    fun onDismissDialog() {
        _screenState.update { it.copy(showCreationDialog = false) }
    }
}

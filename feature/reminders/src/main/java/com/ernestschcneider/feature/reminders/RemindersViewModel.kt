package com.ernestschcneider.feature.reminders

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RemindersViewModel : ViewModel() {
    private val _screenState = MutableStateFlow(RemindersScreenState())
    val screenState: StateFlow<RemindersScreenState> = _screenState.asStateFlow()
    var items = (1..10).map {
        Reminder(
            id = it.toString(), title = "Reminder $it", type = if (it % 3 == 0) {
                ReminderType.Note
            } else ReminderType.List
        )
    }

    fun loadRemindersDetails() {
        _screenState.update { it.copy(reminders = items) }
    }

    fun removeItem(item: Reminder) {
        items = items.filter { it != item }
        _screenState.update { it.copy(reminders = items) }
    }

    fun onAddButtonClicked() {
        _screenState.update { it.copy(showCreationDialog = true) }
    }

    fun onDismissDialog() {
        _screenState.update { it.copy(showCreationDialog = false) }
    }
}

package com.ernestschcneider.feature.reminders

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RemindersViewModel: ViewModel() {
    private val _screenState = MutableStateFlow(RemindersScreenState())
    val screenState: StateFlow<RemindersScreenState> = _screenState.asStateFlow()
    val reminderNote = Reminder(
        title = "GGGGGGGGGG",
        type = ReminderType.Note,
        id = "1"
    )
    val reminderList = Reminder(
        title = "ddd",
        type = ReminderType.List,
        id = "2"
    )
    val reminderNote2 = Reminder(
        title = "dfd",
        type = ReminderType.Note,
        id = "1"
    )
    val reminderList2 = Reminder(
        title = "G",
        type = ReminderType.List,
        id = "2"
    )
    val reminderNote3 = Reminder(
        title = "GGGGGGGGGG",
        type = ReminderType.Note,
        id = "1"
    )
    val reminderList3 = Reminder(
        title = "ddd",
        type = ReminderType.List,
        id = "2"
    )
    val reminderNote4 = Reminder(
        title = "dfd",
        type = ReminderType.Note,
        id = "1"
    )
    val reminderList4 = Reminder(
        title = "G",
        type = ReminderType.List,
        id = "2"
    )
    val items = listOf(
        reminderNote,
        reminderList,
        reminderList2,
        reminderList3,
        reminderNote2,
        reminderNote3,
        reminderNote4,
        reminderList4
    )

    fun loadRemindersDetails() {
        _screenState.update { it.copy(reminders = items) }
    }

    fun removeItem(item: Reminder) {
        val items = items.filter { it != item }.toList()
        _screenState.update { it.copy(reminders = items) }
    }
}

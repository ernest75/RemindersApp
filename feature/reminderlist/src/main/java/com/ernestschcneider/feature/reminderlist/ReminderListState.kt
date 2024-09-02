package com.ernestschcneider.feature.reminderlist

data class ReminderListState(
    val requestFocus: Boolean = false,
    val reminderListTitle: String = "",
    val remindersList: MutableList<String> = mutableListOf(),
    val showSaveButton: Boolean = false,
    val showCreateReminderDialog: Boolean = false,
    val isFirstReminder: Boolean = false
)
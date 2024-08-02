package com.ernestschcneider.feature.reminders

data class RemindersScreenState(
    val reminders: List<Reminder> = emptyList(),
    val showCreationDialog: Boolean = false
)


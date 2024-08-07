package com.ernestschcneider.feature.reminders

import com.ernestschcneider.remindersapp.local.Reminder

data class RemindersScreenState(
    val reminders: List<Reminder> = emptyList(),
    val showCreationDialog: Boolean = false
)


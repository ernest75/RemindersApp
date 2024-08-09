package com.ernestschcneider.feature.reminders

import com.ernestschcneider.feature.reminders.data.models.Reminder

data class RemindersScreenState(
    val reminders: List<Reminder> = emptyList(),
    val showCreationDialog: Boolean = false
)


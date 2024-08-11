package com.ernestschcneider.feature.reminders

import com.ernestschcneider.models.Reminder

data class RemindersScreenState(
    val reminders: List<Reminder> = emptyList(),
    val showCreationDialog: Boolean = false
)


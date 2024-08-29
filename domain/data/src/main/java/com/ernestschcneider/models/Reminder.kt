package com.ernestschcneider.models

import com.ernestschcneider.DEFAULT_UUID

data class Reminder(
    val id: String = DEFAULT_UUID,
    val reminderTitle: String,
    val reminderContent: String,
    val reminderType: ReminderType = ReminderType.Note
)


package com.ernestschcneider.feature.reminders.data.models

import java.util.UUID

data class Reminder(
    val id: UUID = UUID.fromString(DEFAULT_UUID),
    val reminderTitle: String,
    val reminderContent: String,
    val reminderType: ReminderType = ReminderType.Note
)

const val DEFAULT_UUID = "00000000-0000-0000-0000-000000000000"

package com.ernestschcneider.models

data class Reminder(
    val id: String = DEFAULT_UUID,
    val reminderTitle: String,
    val reminderContent: String,
    val reminderType: ReminderType = ReminderType.Note
)

const val DEFAULT_UUID = "00000000-0000-0000-0000-000000000000"

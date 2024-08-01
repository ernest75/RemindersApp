package com.ernestschcneider.feature.reminders

data class Reminder(
    val title: String,
    val type: ReminderType,
    val id: String
)

enum class ReminderType {
    Note,
    List
}

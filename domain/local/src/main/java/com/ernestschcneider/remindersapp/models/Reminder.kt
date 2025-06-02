package com.ernestschcneider.remindersapp.models

data class Reminder(
    val reminderId: String = DEFAULT_UUID,
    val reminderTitle: String,
    val reminderPosition: Int = -1,
    val reminderContent: String = "",
    val remindersList: ArrayList<ReminderListItem> = arrayListOf(),
    val reminderType: ReminderType = ReminderType.Note
)

const val DEFAULT_UUID = "00000000-0000-0000-0000-000000000000"

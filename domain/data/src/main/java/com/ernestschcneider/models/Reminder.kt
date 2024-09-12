package com.ernestschcneider.models

import com.ernestschcneider.DEFAULT_UUID

data class Reminder(
    val reminderId: String = DEFAULT_UUID,
    val reminderTitle: String,
    val reminderPosition: Int = -1,
    val reminderContent: String = "",
    val remindersList: ArrayList<String> = arrayListOf(),
    val reminderType: ReminderType = ReminderType.Note
)

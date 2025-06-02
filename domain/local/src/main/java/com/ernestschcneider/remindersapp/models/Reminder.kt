package com.ernestschcneider.remindersapp.models

import com.ernestschcneider.remindersapp.core.commons.DEFAULT_UUID

data class Reminder(
    val reminderId: String = DEFAULT_UUID,
    val reminderTitle: String,
    val reminderPosition: Int = -1,
    val reminderContent: String = "",
    val remindersList: ArrayList<ReminderListItem> = arrayListOf(),
    val reminderType: ReminderType = ReminderType.Note
)



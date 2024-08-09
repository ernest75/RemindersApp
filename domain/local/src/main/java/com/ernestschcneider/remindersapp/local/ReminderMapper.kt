package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.feature.reminders.data.models.Reminder
import com.ernestschcneider.remindersapp.core.database.ReminderEntity

fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = id,
        reminderTitle = reminderTitle,
        reminderContent = reminderContent,
        reminderType = reminderType
    )
}

fun ReminderEntity.toDomain(): Reminder {
    return Reminder(
        id = id,
        reminderTitle = reminderTitle,
        reminderContent = reminderContent,
        reminderType = reminderType
    )
}

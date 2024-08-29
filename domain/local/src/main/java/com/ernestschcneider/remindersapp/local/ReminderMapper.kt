package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.models.Reminder
import com.ernestschcneider.remindersapp.core.database.ReminderEntity
import java.util.UUID

fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = UUID.fromString(id),
        reminderTitle = reminderTitle,
        reminderContent = reminderContent,
        reminderType = reminderType,
        remindersList = remindersList
    )
}

fun ReminderEntity.toDomain(): Reminder {
    return Reminder(
        id = id.toString(),
        reminderTitle = reminderTitle,
        reminderContent = reminderContent,
        reminderType = reminderType,
        remindersList = remindersList
    )
}

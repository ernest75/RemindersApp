package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.models.Reminder
import com.ernestschcneider.remindersapp.core.database.ReminderEntity
import com.ernestschcneider.remindersapp.core.database.ReminderEntity.Companion.DEFAULT_UUID
import java.util.UUID

fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = UUID.fromString(DEFAULT_UUID),
        reminderTitle = reminderTitle,
        reminderContent = reminderContent,
        reminderType = reminderType
    )
}

fun ReminderEntity.toDomain(): Reminder {
    return Reminder(
        id = id.toString(),
        reminderTitle = reminderTitle,
        reminderContent = reminderContent,
        reminderType = reminderType
    )
}

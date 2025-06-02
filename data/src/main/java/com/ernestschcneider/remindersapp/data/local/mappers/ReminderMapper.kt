package com.ernestschcneider.remindersapp.data.local.mappers

import com.ernestschcneider.remindersapp.models.Reminder
import com.ernestschcneider.remindersapp.data.local.database.ReminderEntity
import java.util.UUID

fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = UUID.fromString(reminderId),
        reminderPosition = reminderPosition,
        reminderTitle = reminderTitle,
        reminderContent = reminderContent,
        reminderType = reminderType,
        remindersList = remindersList
    )
}

fun ReminderEntity.toDomain(): Reminder {
    return Reminder(
        reminderId = id.toString(),
        reminderPosition = reminderPosition,
        reminderTitle = reminderTitle,
        reminderContent = reminderContent,
        reminderType = reminderType,
        remindersList = remindersList
    )
}

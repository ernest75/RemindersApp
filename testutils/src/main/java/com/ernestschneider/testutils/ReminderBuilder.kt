package com.ernestschneider.testutils

import com.ernestschcneider.feature.reminders.data.models.DEFAULT_UUID
import com.ernestschcneider.feature.reminders.data.models.Reminder
import com.ernestschcneider.feature.reminders.data.models.ReminderType
import java.util.UUID

class ReminderBuilder {
    private var id: UUID = UUID.fromString(DEFAULT_UUID)
    private var reminderTitle: String = ""
    private var reminderContent: String = ""
    private var reminderType = ReminderType.Note

    fun withReminderTitle(reminderTitle: String) = apply {
        this.reminderTitle = reminderTitle
    }

    fun withReminderContent(reminderContent: String) = apply {
        this.reminderContent = reminderContent
    }

    fun withReminderType(reminderType: ReminderType) = apply {
        this.reminderType = reminderType
    }


    fun build(): Reminder {
        return Reminder(
            id = id,
            reminderTitle = reminderTitle,
            reminderContent = reminderContent
        )
    }

    companion object {
        fun aReminder() = ReminderBuilder()
    }
}
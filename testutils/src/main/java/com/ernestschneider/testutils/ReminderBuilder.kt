package com.ernestschneider.testutils

import com.ernestschcneider.models.DEFAULT_UUID
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderType
import java.util.UUID

class ReminderBuilder {
    private var id: String = ""
    private var reminderTitle: String = ""
    private var reminderContent: String = ""
    private var reminderType = ReminderType.Note

    fun withId(id: String) = apply {
        this.id = id
    }

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
package com.ernestschneider.testutils

import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderType

class ReminderBuilder {
    private var id: String = ""
    private var reminderTitle: String = ""
    private var reminderContent: String = ""
    private var reminderType = ReminderType.Note
    private var reminderList: ArrayList<String> = arrayListOf()

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

    fun withReminderList(reminderList: ArrayList<String>) = apply {
        this.reminderList = reminderList
    }


    fun build(): Reminder {
        return Reminder(
            reminderId = id,
            reminderTitle = reminderTitle,
            reminderContent = reminderContent,
            reminderType = reminderType,
            remindersList = reminderList
        )
    }

    companion object {
        fun aReminder() = ReminderBuilder()
    }
}
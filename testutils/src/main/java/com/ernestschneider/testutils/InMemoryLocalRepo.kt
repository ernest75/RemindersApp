package com.ernestschneider.testutils

import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.local.StorageRepo

class InMemoryLocalRepo : StorageRepo {

    override suspend fun getAllReminders(): List<Reminder> {
        return reminders
    }

    override suspend fun saveReminder(reminder: Reminder) {
        reminders.add(reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminders.remove(reminder)
    }

    override suspend fun getReminder(reminderId: String): Reminder {
        return reminders[reminderId.toInt() -1]
    }

    override suspend fun updateReminder(reminder: Reminder) {
        val index = reminder.id.toInt() -1
        reminders.removeAt(index)
        reminders.add(index, reminder)
    }

    fun getReminders(): List<Reminder> {
        return reminders
    }

    fun getReminderAt(position: Int): Reminder {
        return reminders[position]
    }

    private val reminders = (1..10).map {
        ReminderBuilder
            .aReminder()
            .withId(it.toString())
            .withReminderTitle("Title$it")
            .withReminderContent("Content$it")
            .withReminderType(
                if (it % 3 == 0) {
                    ReminderType.Note
                } else {
                    ReminderType.List
                }
            )
            .withReminderList(
                if (it % 3 != 0) {
                    arrayListOf("Element1", "Element2")
                } else {
                    arrayListOf()
                }
            )
            .build()
    }.toMutableList()
}
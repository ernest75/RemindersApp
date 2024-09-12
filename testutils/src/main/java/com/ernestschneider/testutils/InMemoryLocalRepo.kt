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
        return reminders[reminderId.toInt() - 1]
    }

    override suspend fun updateReminder(reminder: Reminder) {
        val index = reminder.reminderId.toInt() - 1
        reminders.removeAt(index)
        reminders.add(index, reminder)
    }

    override suspend fun updateReminderPosition(position: Int, reminderId: String) {
        val reminder = findReminderById(reminderId)
        reminders.remove(reminder)
        val reminderChanged = Reminder(
            reminderId = reminder.reminderId,
            reminderTitle = reminder.reminderTitle,
            reminderContent = reminder.reminderContent,
            reminderPosition = position
        )
        reminders.add(position, reminderChanged)
    }

    private fun findReminderById(reminderId: String): Reminder {
       return reminders.first { it.reminderId == reminderId }
    }

    fun getReminders(): List<Reminder> {
        return reminders
    }

    fun getReminderAt(position: Int): Reminder {
        return reminders[position]
    }

    fun saveReminders(reminders : List<Reminder>) {
        this.reminders.addAll(reminders)
    }

//    private val reminderNote1 = ReminderBuilder.aReminder()
//        .withId("1")
//        .withReminderTitle("Title1")
//        .withReminderContent("Content1")
//        .withReminderType(ReminderType.Note)
//        .build()
//
//    private val reminderList1 = ReminderBuilder.aReminder()
//        .withId("2")
//        .withReminderTitle("Title2")
//        .withReminderType(ReminderType.List)
//        .withReminderList(arrayListOf("Element1", "Element2"))
//        .build()


    private var reminders = mutableListOf<Reminder>()
}
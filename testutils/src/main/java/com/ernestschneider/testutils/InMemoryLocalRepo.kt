package com.ernestschneider.testutils

import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.remindersapp.local.StorageRepo

class InMemoryLocalRepo(private val reminders: MutableList<Reminder> = mutableListOf()) : StorageRepo {

    override suspend fun getAllReminders(): List<Reminder> {
        return reminders
    }

    override suspend fun saveReminder(reminder: Reminder) {
        reminders.add(reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminders.removeAt(reminder.reminderPosition)
    }

    override suspend fun getReminder(reminderId: String): Reminder {
        return findReminderById(reminderId)
    }

    override suspend fun updateReminder(reminder: Reminder) {
        val savedReminder = findReminderById(reminder.reminderId)
        val index = reminders.indexOf(savedReminder)
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
            remindersList = reminder.remindersList,
            reminderPosition = position
        )
        reminders.add(position, reminderChanged)
    }

    override suspend fun updateReminderList(reminderList: ArrayList<ReminderListItem>, reminderId: String) {
        val reminder = findReminderById(reminderId)
        reminders.remove(reminder)
        val reminderChanged = Reminder(
            reminderId = reminder.reminderId,
            reminderTitle = reminder.reminderTitle,
            reminderContent = reminder.reminderContent,
            remindersList = reminderList,
            reminderPosition = reminder.reminderPosition
        )
        reminders.add(reminder.reminderPosition, reminderChanged)
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
}
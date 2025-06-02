package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.remindersapp.models.Reminder
import com.ernestschcneider.remindersapp.models.ReminderListItem

interface StorageRepo {
    suspend fun getAllReminders():List<Reminder>

    suspend fun saveReminder(reminder: Reminder)

    suspend fun deleteReminder(reminder: Reminder)

    suspend fun getReminder(reminderId: String): Reminder

    suspend fun updateReminder(reminder: Reminder)

    suspend fun updateReminderPosition(position: Int, reminderId: String)

    suspend fun updateReminderList(reminderList: ArrayList<ReminderListItem>, reminderId: String)
}

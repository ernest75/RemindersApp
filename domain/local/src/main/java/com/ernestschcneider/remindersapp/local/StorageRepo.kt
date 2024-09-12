package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.models.Reminder
import java.util.UUID

interface StorageRepo {
    suspend fun getAllReminders():List<Reminder>

    suspend fun saveReminder(reminder: Reminder)

    suspend fun deleteReminder(reminder: Reminder)

    suspend fun getReminder(reminderId: String): Reminder

    suspend fun updateReminder(reminder: Reminder)

    suspend fun updateReminderPosition(position: Int, reminderId: String)
}

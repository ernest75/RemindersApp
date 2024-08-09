package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.feature.reminders.data.models.Reminder

interface StorageRepo {
    suspend fun getAllReminders():List<Reminder>

    suspend fun saveReminder(reminder: Reminder)

    suspend fun deleteReminder(reminder: Reminder)
}

package com.ernestschcneider.remindersapp.local

interface StorageRepo {
    suspend fun getAllReminders():List<Reminder>

    suspend fun saveReminder(reminder: Reminder)

    suspend fun deleteReminder(reminder: Reminder)
}

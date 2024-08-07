package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.remindersapp.core.database.ReminderDao
import javax.inject.Inject


class LocalRepo @Inject constructor(
    private val reminderDao: ReminderDao
): StorageRepo {

    override suspend fun getAllReminders():List<Reminder>{
      return reminderDao.loadAll().map {
           it.toDomain()
       }
    }

    override suspend fun saveReminder(reminder: Reminder) {
       reminderDao.insert(reminder.toReminderEntity())
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(reminder.toReminderEntity())
    }
}

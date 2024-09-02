package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.models.Reminder
import com.ernestschcneider.remindersapp.core.database.ReminderDao
import java.util.UUID
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

    override suspend fun getReminder(reminderId: String ): Reminder {
       return reminderDao.getReminderById(UUID.fromString(reminderId)).toDomain()
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.update(reminder.toReminderEntity())
    }
}

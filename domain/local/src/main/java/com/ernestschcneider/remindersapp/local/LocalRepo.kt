package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.remindersapp.core.database.ReminderDao
import java.util.ArrayList
import java.util.UUID
import javax.inject.Inject


class LocalRepo @Inject constructor(
    private val reminderDao: ReminderDao
): StorageRepo {

    override suspend fun getAllReminders():List<Reminder>{
      return reminderDao.getAllReminders().map {
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

    override suspend fun updateReminderPosition(position: Int, reminderId: String) {
        reminderDao.updateReminderPosition(position, UUID.fromString(reminderId))
    }

    override suspend fun updateReminderList(reminderList: ArrayList<ReminderListItem>, reminderId: String) {
        reminderDao.updateReminderList(reminderList, UUID.fromString(reminderId))
    }
}

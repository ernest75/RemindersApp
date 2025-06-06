package com.ernestschcneider.remindersapp.data.local.datasource

import com.ernestschcneider.remindersapp.data.local.database.ReminderDao
import com.ernestschcneider.remindersapp.data.local.database.ReminderEntity
import com.ernestschcneider.remindersapp.models.Reminder
import com.ernestschcneider.remindersapp.models.ReminderListItem
import java.util.UUID
import javax.inject.Inject

class ReminderLocalDataSourceImpl @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderLocalDataSource {
    override suspend fun getAllReminders(): List<ReminderEntity> = reminderDao.getAllReminders()


    override suspend fun saveReminder(reminder: ReminderEntity) {
        reminderDao.insert(reminder)
    }

    override suspend fun deleteReminder(reminder: ReminderEntity) {
        reminderDao.delete(reminder)
    }

    override suspend fun getReminder(reminderId: String): ReminderEntity =
        reminderDao.getReminderById(UUID.fromString(reminderId))

    override suspend fun updateReminder(reminder: ReminderEntity) {
        reminderDao.update(reminder)
    }

    override suspend fun updateReminderPosition(position: Int, reminderId: String) {
        reminderDao.updateReminderPosition(position, UUID.fromString(reminderId))
    }

    override suspend fun updateReminderList(
        reminderList: ArrayList<ReminderListItem>,
        reminderId: String
    ) {
        reminderDao.updateReminderList(reminderList, UUID.fromString(reminderId))
    }

    override suspend fun countReminders(): Int =
       reminderDao.countReminders()
}

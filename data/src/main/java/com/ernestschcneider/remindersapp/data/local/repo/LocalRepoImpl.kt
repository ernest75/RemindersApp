package com.ernestschcneider.remindersapp.data.local.repo

import com.ernestschcneider.remindersapp.models.Reminder
import com.ernestschcneider.remindersapp.models.ReminderListItem
import com.ernestschcneider.remindersapp.data.local.database.ReminderDao
import com.ernestschcneider.remindersapp.data.local.datasource.ReminderLocalDataSource
import com.ernestschcneider.remindersapp.local.LocalRepo
import com.ernestschcneider.remindersapp.data.local.mappers.toDomain
import com.ernestschcneider.remindersapp.data.local.mappers.toReminderEntity
import java.util.ArrayList
import java.util.UUID
import javax.inject.Inject


class LocalRepoImpl @Inject constructor(
    private val reminderLocalDataSource: ReminderLocalDataSource
): LocalRepo {

    override suspend fun getAllReminders():List<Reminder>{
      return reminderLocalDataSource.getAllReminders().map {
           it.toDomain()
       }
    }

    override suspend fun saveReminder(reminder: Reminder) {
       reminderLocalDataSource.saveReminder(reminder.toReminderEntity())
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderLocalDataSource.deleteReminder(reminder.toReminderEntity())
    }

    override suspend fun getReminder(reminderId: String ): Reminder {
       return reminderLocalDataSource.getReminder(reminderId).toDomain()
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderLocalDataSource.updateReminder(reminder.toReminderEntity())
    }

    override suspend fun updateReminderPosition(position: Int, reminderId: String) {
        reminderLocalDataSource.updateReminderPosition(position, reminderId)
    }

    override suspend fun updateReminderList(reminderList: ArrayList<ReminderListItem>, reminderId: String) {
        reminderLocalDataSource.updateReminderList(reminderList, reminderId)
    }

    override suspend fun countReminders(): Int = reminderLocalDataSource.countReminders()
}

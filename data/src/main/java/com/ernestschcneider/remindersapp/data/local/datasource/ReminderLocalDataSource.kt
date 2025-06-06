package com.ernestschcneider.remindersapp.data.local.datasource

import com.ernestschcneider.remindersapp.data.local.database.ReminderEntity
import com.ernestschcneider.remindersapp.models.ReminderListItem

interface ReminderLocalDataSource {
    suspend fun getAllReminders(): List<ReminderEntity>
    suspend fun saveReminder(reminder: ReminderEntity)
    suspend fun deleteReminder(reminder: ReminderEntity)
    suspend fun getReminder(reminderId: String): ReminderEntity
    suspend fun updateReminder(reminder: ReminderEntity)
    suspend fun updateReminderPosition(position: Int, reminderId: String)
    suspend fun updateReminderList(reminderList: ArrayList<ReminderListItem>, reminderId: String)
    suspend fun countReminders(): Int
}
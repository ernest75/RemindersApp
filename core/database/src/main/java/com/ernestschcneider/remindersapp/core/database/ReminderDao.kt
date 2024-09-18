package com.ernestschcneider.remindersapp.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.util.UUID

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders")
    suspend fun getAllReminders(): List<ReminderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderEntity)

    @Delete
    fun delete(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE id= :id")
    fun getReminderById(id: UUID): ReminderEntity

    @Update
    suspend fun update(reminder: ReminderEntity)

    @Query("UPDATE reminders SET reminderPosition=:position WHERE id = :id")
    fun updateReminderPosition(position: Int, id: UUID)

    @Query("UPDATE reminders SET remindersList=:remindersList WHERE id = :id")
    fun updateReminderList(remindersList: ArrayList<String>, id:UUID)

}

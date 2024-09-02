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
    suspend fun loadAll(): List<ReminderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderEntity)

    @Delete
    fun delete(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE id= :id")
    fun getReminderById(id: UUID): ReminderEntity

    @Update
    suspend fun update(reminder: ReminderEntity)
}

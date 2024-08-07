package com.ernestschcneider.remindersapp.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders")
    suspend fun loadAll(): List<ReminderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: ReminderEntity)

    @Delete
    fun delete(note: ReminderEntity)
}

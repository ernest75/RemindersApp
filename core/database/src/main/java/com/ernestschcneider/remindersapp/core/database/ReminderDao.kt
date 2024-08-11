package com.ernestschcneider.remindersapp.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.UUID

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders")
    suspend fun loadAll(): List<ReminderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: ReminderEntity)

    @Delete
    fun delete(note: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE id= :id")
    fun getReminderById(id: UUID): ReminderEntity
}

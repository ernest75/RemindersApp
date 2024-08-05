package com.ernestschcneider.remindersapp.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    suspend fun loadAll(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: NoteEntity)

    @Delete
    fun delete(user: NoteEntity)
}
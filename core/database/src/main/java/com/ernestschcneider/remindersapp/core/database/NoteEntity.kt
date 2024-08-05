package com.ernestschcneider.remindersapp.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "about") val about: String
)

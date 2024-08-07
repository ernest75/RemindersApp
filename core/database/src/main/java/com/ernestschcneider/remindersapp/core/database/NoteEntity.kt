package com.ernestschcneider.remindersapp.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey val id: UUID = UUID.fromString(DEFAULT_UUID),
    @ColumnInfo(name = "noteTitle") val noteTitle: String,
    @ColumnInfo(name = "noteContent") val noteContent: String
){

    companion object {
        const val DEFAULT_UUID = "00000000-0000-0000-0000-000000000000"
    }
}

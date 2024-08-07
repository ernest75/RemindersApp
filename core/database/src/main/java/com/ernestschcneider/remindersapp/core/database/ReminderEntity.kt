package com.ernestschcneider.remindersapp.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ernestschcneider.feature.reminders.data.models.ReminderType
import java.util.UUID

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: UUID = UUID.fromString(DEFAULT_UUID),
    @ColumnInfo(name = "reminderTitle") val reminderTitle: String,
    @ColumnInfo(name = "noteContent") val reminderContent: String,
    @ColumnInfo(name = "noteType") val reminderType: ReminderType
){

    companion object {
        const val DEFAULT_UUID = "00000000-0000-0000-0000-000000000000"
    }
}

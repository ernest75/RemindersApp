package com.ernestschcneider.remindersapp.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ernestschcneider.remindersapp.models.DEFAULT_UUID
import com.ernestschcneider.remindersapp.models.ReminderListItem
import com.ernestschcneider.remindersapp.models.ReminderType
import java.util.UUID

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: UUID = UUID.fromString(DEFAULT_UUID),
    @ColumnInfo(name = "reminderPosition") val reminderPosition: Int,
    @ColumnInfo(name = "reminderTitle") val reminderTitle: String,
    @ColumnInfo(name = "noteContent") val reminderContent: String,
    @ColumnInfo(name = "noteType") val reminderType: ReminderType,
    @ColumnInfo(name = "remindersList") val remindersList: ArrayList<ReminderListItem>
)

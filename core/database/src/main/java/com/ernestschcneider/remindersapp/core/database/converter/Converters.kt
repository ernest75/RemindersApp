package com.ernestschcneider.remindersapp.core.database.converter

import androidx.room.TypeConverter
import com.ernestschcneider.remindersapp.core.database.NoteEntity
import java.util.UUID

class Converters {

    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        if ( uuid.toString() == NoteEntity.DEFAULT_UUID )
            return UUID.randomUUID().toString()

        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID {
        string?.let {
            return UUID.fromString(string)
        }
        return UUID.fromString(NoteEntity.DEFAULT_UUID)
    }

}
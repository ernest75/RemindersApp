package com.ernestschcneider.remindersapp.core.database.converter

import androidx.room.TypeConverter
import com.ernestschcneider.DEFAULT_UUID
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.remindersapp.core.database.ReminderEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

class Converters {

    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        if (uuid.toString() == DEFAULT_UUID )
            return UUID.randomUUID().toString()

        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID {
        string?.let {
            return UUID.fromString(string)
        }
        return UUID.fromString(DEFAULT_UUID)
    }

    @TypeConverter
    fun fromStringArrayList(value: ArrayList<ReminderListItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): ArrayList<ReminderListItem> {
        return try {
            Gson().fromJson<ArrayList<ReminderListItem>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)
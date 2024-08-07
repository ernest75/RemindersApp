package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.remindersapp.core.database.NoteDao
import javax.inject.Inject


class LocalRepo @Inject constructor(
    private val noteDao: NoteDao
): StorageRepo {

    override suspend fun getAllReminders():List<Note>{
      return noteDao.loadAll().map {
           it.toDomain()
       }
    }

    override suspend fun saveNote(note: Note) {
       noteDao.insert(note.toNoteEntity())
    }
}

package com.ernestschcneider.remindersapp.local

interface StorageRepo {
    suspend fun getAllReminders():List<Note>

    suspend fun saveNote(note: Note)
}
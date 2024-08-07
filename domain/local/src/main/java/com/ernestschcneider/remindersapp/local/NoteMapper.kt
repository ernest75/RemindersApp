package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.remindersapp.core.database.NoteEntity

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        uid = id,
        noteTitle = noteTitle,
        noteContent = noteContent
    )
}

fun NoteEntity.toDomain(): Note {
    return Note(
        id = uid,
        noteTitle = noteTitle,
        noteContent = noteContent
    )
}
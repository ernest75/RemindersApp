package com.ernestschcneider.remindersapp.local

import com.ernestschcneider.remindersapp.core.database.NoteEntity

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        noteTitle = noteTitle,
        noteContent = noteContent
    )
}

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        noteTitle = noteTitle,
        noteContent = noteContent
    )
}

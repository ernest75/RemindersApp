package com.ernestschcneider.remindersapp.local

import java.util.UUID

data class Note(
    val id: UUID = UUID.fromString(DEFAULT_UUID),
    val noteTitle: String,
    val noteContent: String
)

const val DEFAULT_UUID = "00000000-0000-0000-0000-000000000000"

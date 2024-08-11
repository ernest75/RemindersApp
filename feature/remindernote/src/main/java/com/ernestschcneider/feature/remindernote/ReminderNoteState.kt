package com.ernestschcneider.feature.remindernote

data class ReminderNoteState(
    val noteTitle: String = "",
    val noteContent: String = "",
    val backNavigation: Boolean = false,
    val showEmptyTitleDialog: Boolean = false,
    val requestFocus: Boolean = false
)

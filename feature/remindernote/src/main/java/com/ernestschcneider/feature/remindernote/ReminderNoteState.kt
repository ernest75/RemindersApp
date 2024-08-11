package com.ernestschcneider.feature.remindernote

data class ReminderNoteState(
    val reminderTitle: String = "",
    val reminderContent: String = "",
    val backNavigation: Boolean = false,
    val showEmptyTitleDialog: Boolean = false,
    val requestFocus: Boolean = false,
    val showSaveButton: Boolean = true
)

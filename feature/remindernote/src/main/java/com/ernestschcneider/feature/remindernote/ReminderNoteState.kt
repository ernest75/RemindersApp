package com.ernestschcneider.feature.remindernote

import androidx.compose.ui.text.input.TextFieldValue

data class ReminderNoteState(
    val reminderTitle: TextFieldValue = TextFieldValue(""),
    val reminderContent: String = "",
    val backNavigation: Boolean = false,
    val showEmptyTitleDialog: Boolean = false,
    val requestFocus: Boolean = false,
    val showSaveButton: Boolean = true
)
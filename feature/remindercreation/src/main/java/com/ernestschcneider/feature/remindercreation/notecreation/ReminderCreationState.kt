package com.ernestschcneider.feature.remindercreation.notecreation

data class ReminderCreationState(
    val noteTitle: String = "",
    val noteContent: String = "",
    val backNavigation: Boolean = false,
    val showEmptyTitleDialog: Boolean = false
)

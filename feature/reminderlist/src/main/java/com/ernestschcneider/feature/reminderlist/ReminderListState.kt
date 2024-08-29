package com.ernestschcneider.feature.reminderlist

data class ReminderListState(
    val requestFocus: Boolean = false,
    val reminderListTitle: String = ""
)
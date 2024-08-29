package com.ernestschcneider.feature.reminderlist

import com.ernestschcneider.models.RemindersListItemModel

data class ReminderListState(
    val requestFocus: Boolean = false,
    val reminderListTitle: String = "",
    val remindersList: List<RemindersListItemModel> = emptyList(),
    val showSaveButton: Boolean = true
)
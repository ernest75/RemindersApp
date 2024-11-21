package com.ernestschcneider.feature.reminderlist

import androidx.compose.runtime.Stable
import com.ernestschcneider.models.ReminderListItem

@Stable
data class ReminderListState(
    val requestFocus: Boolean = false,
    val reminderListTitle: String = "",
    val remindersList: List<ReminderListItem> = listOf(),
    val showSaveButton: Boolean = false,
    val showCreateReminderDialog: Boolean = false,
    val isFirstReminder: Boolean = false,
    val backNavigation: Boolean = false,
    val showEmptyTitleDialog: Boolean = false,
    val scrollListToLast: Boolean = false,
    val reminderToEdit: ReminderListItem = ReminderListItem()
)

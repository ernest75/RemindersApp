package com.ernestschcneider.feature.reminders

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REMINDERS_ROUTE = "reminders"

fun NavGraphBuilder.remindersScreen(
    onNavigateUp: () -> Unit,
    onReminderClick: (reminderId: String) -> Unit,
    onListReminderClick: (listReminderId: String) -> Unit,
    onReminderCreationClick: () -> Unit,
    onListReminderCreationClick: () -> Unit
){
    composable(REMINDERS_ROUTE) {
        RemindersScreen(
            onNavigateUp = onNavigateUp,
            onReminderClicked = onReminderClick,
            onReminderCreationClick = onReminderCreationClick,
            onListReminderCreationClick = onListReminderCreationClick,
            onListReminderClick = onListReminderClick
        )
    }
}

fun NavController.navigateToReminders() {
    navigate(REMINDERS_ROUTE)
}

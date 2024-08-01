package com.ernestschcneider.feature.reminders

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REMINDERS_ROUTE = "reminders"

fun NavGraphBuilder.remindersScreen(
    onNavigateUp: () -> Unit,
    onReminderClick: (reminderId: String) -> Unit
){
    composable(REMINDERS_ROUTE) {
        RemindersScreen(
            onNavigateUp = onNavigateUp,
            onItemClicked = onReminderClick
        )
    }
}

fun NavController.navigateToReminders() {
    navigate(REMINDERS_ROUTE)
}

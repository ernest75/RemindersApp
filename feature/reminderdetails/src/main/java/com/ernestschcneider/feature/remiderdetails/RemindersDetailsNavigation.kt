package com.ernestschcneider.feature.remiderdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REMINDER_DETAILS_ROUTE = "remindersDetails"

fun NavGraphBuilder.remindersDetailScreen(
    onNavigateUp: () -> Unit
) {
    composable(REMINDER_DETAILS_ROUTE) {
        RemindersDetailScreen(
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToReminderDetailsScreen() {
    navigate(REMINDER_DETAILS_ROUTE)
}

@Composable
internal fun RemindersDetailScreen(
    onNavigateUp: () -> Unit
) {
    Column {
        Text(text = "Reminder Details")
        TextButton(onClick = onNavigateUp) {
            Text(text = "Go back")
        }
    }
}

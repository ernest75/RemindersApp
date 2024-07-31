package com.ernestschcneider.feature.reminders

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REMINDERS_ROUTE = "reminders"

fun NavGraphBuilder.remindersScreen(
    onReminderClick: (reminderId: String) -> Unit
){
    composable(REMINDERS_ROUTE) {
        RemindersScreen(
            onReminderDetail = onReminderClick
        )
    }
}

fun NavController.navigateToReminders() {
    navigate(REMINDERS_ROUTE)
}

@Composable
internal fun RemindersScreen(
    onReminderDetail: (reminderId: String) -> Unit
) {
    Column {
        Text(text = "RemindersScreen")
        TextButton(onClick = { onReminderDetail("reminderId") }) {
            Text(text = "ToDetail")
        }
    }
}

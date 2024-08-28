package com.ernestschcneider.feature.reminderlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun ReminderListScreen(
    onNavigateUp: () -> Unit
){

    ReminderListScreenContent(

    )

}

@Composable
fun ReminderListScreenContent() {
    Text(text = "Remider list")
}

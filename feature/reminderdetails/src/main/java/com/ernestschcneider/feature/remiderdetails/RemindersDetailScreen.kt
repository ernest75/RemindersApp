package com.ernestschcneider.feature.remiderdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun ReminderDetailScreen(
    viewModel: ReminderDetailsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loadRemindersDetails()
    }
    ReminderDetailScreenContent(
        onNavigateUp,
        screenState
    )
}

@Composable
private fun ReminderDetailScreenContent(
    onNavigateUp: () -> Unit,
    screenState: ReminderDetailsScreenState
) {
    Column {
        Text(text = "Reminder Details")
        Text(text = screenState.title)
        TextButton(onClick = onNavigateUp) {
            Text(text = "Go back")
        }
    }
}

@Composable
@PreviewLightDark
private fun ReminderDetailsScreenPreview() {
AppTheme{
    ReminderDetailScreenContent(
        screenState = ReminderDetailsScreenState("Reminder Title"),
        onNavigateUp = {}
    )
}
}
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
internal fun ReminderDetailScreenContainer(
    viewModel: ReminderDetailsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loadRemindersDetails()
    }
    ReminderDetailScreen(
        onNavigateUp,
        screenState
    )
}

@Composable
private fun ReminderDetailScreen(
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
    ReminderDetailScreen(
        screenState = ReminderDetailsScreenState("Reminder Title"),
        onNavigateUp = {}
    )
}
}
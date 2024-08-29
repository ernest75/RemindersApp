package com.ernestschcneider.feature.reminderlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.feature.reminderlist.views.AddReminder
import com.ernestschcneider.feature.reminderlist.views.RemindersListItem
import com.ernestschcneider.models.RemindersListItemModel
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.composables.RemindersTopAppBar
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun ReminderListScreen(
    reminderListViewModel: ReminderListViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by reminderListViewModel.screenState.collectAsStateWithLifecycle()

    ReminderListScreenContent(
        onNavigateUp = onNavigateUp,
        screenState = state
    )
}

@Composable
fun ReminderListScreenContent(
    onNavigateUp: () -> Unit,
    screenState: ReminderListState
) {
    val focusRequester = remember { FocusRequester() }
    if (screenState.requestFocus) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
            .background(AppTheme.colorScheme.secondaryContainer)
    ) {
        RemindersTopAppBar(
            onNavigateUp = onNavigateUp,
            onTitleUpdate = {},
            focusRequester = focusRequester,
            value = screenState.reminderListTitle,
            titlePlaceHolderId = R.string.type_reminder_title
        )
        AddReminder(
            onAddReminderClicked = {}
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 24.dp),
            color = AppTheme.colorScheme.scrim
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(screenState.remindersList) {
                RemindersListItem(
                    item = it,
                    editReminder = { },
                    deleteReminder = {}
                )
            }
        }
        AddReminder(
            onAddReminderClicked = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun NoteCreationScreenPreview() {
    AppTheme {
        ReminderListScreenContent(
            onNavigateUp = {},
            screenState = ReminderListState(
                remindersList = listOf(
                    RemindersListItemModel(
                        reminderContent = "Hello"
                    ),
                    RemindersListItemModel(
                        reminderContent = "Hello2"
                    )
                )
            )
        )
    }
}


package com.ernestschcneider.feature.reminderlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.feature.reminderlist.views.AddReminder
import com.ernestschcneider.feature.reminderlist.views.AddReminderDialog
import com.ernestschcneider.feature.reminderlist.views.RemindersListItem
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.composables.PrimaryButton
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
        screenState = state,
        onReminderListTitleUpdate = reminderListViewModel::onReminderListTitleUpdate,
        onAddFirstReminder = reminderListViewModel::onFirstReminderListClicked,
        onDismissDialogClicked = reminderListViewModel::onDismissDialogClicked
    )
}

@Composable
fun ReminderListScreenContent(
    onNavigateUp: () -> Unit,
    screenState: ReminderListState,
    onReminderListTitleUpdate: (String) -> Unit,
    onAddFirstReminder: () -> Unit,
    onDismissDialogClicked: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    if (screenState.requestFocus) {
        focusRequester.requestFocus()
    }
    Scaffold(
        modifier = Modifier
            .padding(top = 48.dp)
            .fillMaxSize(),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.save_reminder_list),
                onClick = { },
                isVisible = screenState.showSaveButton
            )
        },
        topBar = {
            RemindersTopAppBar(
                onNavigateUp = onNavigateUp,
                onTitleUpdate = onReminderListTitleUpdate,
                focusRequester = focusRequester,
                value = screenState.reminderListTitle,
                titlePlaceHolderId = R.string.type_reminder_title
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.primaryContainer)
                .padding(paddingValues)
        ) {
            AddReminder(
                onAddReminderClicked = onAddFirstReminder
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
                        editReminder = {},
                        deleteReminder = {}
                    )
                }
            }
            AddReminder(
                onAddReminderClicked = {}
            )
            if (screenState.showCreateReminderDialog) {
                AddReminderDialog(
                    focusRequester = focusRequester,
                    onDismiss = onDismissDialogClicked
                ) {

                }
            }
        }
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
                    "Hello",
                    "Hello2"
                )
            ),
            onReminderListTitleUpdate = {},
            onAddFirstReminder = {},
            onDismissDialogClicked = {}
        )
    }
}

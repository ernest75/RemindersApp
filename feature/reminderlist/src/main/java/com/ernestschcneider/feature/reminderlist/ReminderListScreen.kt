package com.ernestschcneider.feature.reminderlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.ernestschcneider.remindersapp.core.view.composables.InformativeDialog
import com.ernestschcneider.remindersapp.core.view.composables.PrimaryButton
import com.ernestschcneider.remindersapp.core.view.composables.RemindersTopAppBar
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun ReminderListScreen(
    reminderListViewModel: ReminderListViewModel = hiltViewModel(), onNavigateUp: () -> Unit
) {
    val state by reminderListViewModel.screenState.collectAsStateWithLifecycle()
    if (state.backNavigation) {
        onNavigateUp()
    }
    LaunchedEffect(Unit) {
        reminderListViewModel.loadReminderList()
    }

    ReminderListScreenContent(
        onNavigateUp = onNavigateUp,
        screenState = state,
        onReminderListTitleUpdate = reminderListViewModel::onReminderListTitleUpdate,
        onAddFirstReminder = reminderListViewModel::onAddFirstReminderListClicked,
        onFirstReminderAdded = reminderListViewModel::onFirstReminderListItemAdded,
        onAddLastReminder = reminderListViewModel::onAddLastReminderListClicked,
        onLastReminderAdded = reminderListViewModel::onLastReminderListItemAdded,
        onDismissCreateDialogClicked = reminderListViewModel::onDismissCreateDialogClicked,
        onDismissEmptyTitleClicked = reminderListViewModel::onDismissEmptyTitleDialogClicked,
        onSaveReminderClicked = reminderListViewModel::onSaveListReminderClicked,
        onDeleteReminder = reminderListViewModel::onDeleteReminderItem,
        onEditReminder = reminderListViewModel::onReminderEditClicked,
        onReminderEdited = reminderListViewModel::onReminderEdited
    )
}

@Composable
fun ReminderListScreenContent(
    onNavigateUp: () -> Unit,
    screenState: ReminderListState,
    onReminderListTitleUpdate: (String) -> Unit,
    onAddFirstReminder: () -> Unit,
    onFirstReminderAdded: (String) -> Unit,
    onAddLastReminder: () -> Unit,
    onLastReminderAdded: (String) -> Unit,
    onDismissCreateDialogClicked: () -> Unit,
    onDismissEmptyTitleClicked: () -> Unit,
    onSaveReminderClicked: () -> Unit,
    onDeleteReminder: (String) -> Unit,
    onEditReminder: (ReminderItem) -> Unit,
    onReminderEdited: (ReminderItem) -> Unit
) {
    val listState = rememberLazyListState()

    if (screenState.scrollListToLast) {
        LaunchedEffect(Unit) {
            listState.scrollToItem(screenState.remindersList.size)
        }
    }
    val focusRequester = remember { FocusRequester() }
    if (screenState.requestFocus) {
        focusRequester.requestFocus()
    }
    Scaffold(modifier = Modifier
        .padding(top = 48.dp)
        .fillMaxSize(), topBar = {
        RemindersTopAppBar(
            onNavigateUp = onNavigateUp,
            onTitleUpdate = onReminderListTitleUpdate,
            focusRequester = focusRequester,
            value = screenState.reminderListTitle,
            titlePlaceHolderId = R.string.type_reminder_title
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.primaryContainer)
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                state = listState
            ) {
                item {
                    AddReminder(
                        modifier = Modifier.padding(top = 24.dp),
                        onAddReminderClicked = onAddFirstReminder
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 24.dp), color = AppTheme.colorScheme.scrim
                    )
                }
                items(screenState.remindersList) {
                    RemindersListItem(
                        item = ReminderItem(
                            pos = screenState.remindersList.indexOf(it),
                            text = it
                        ),
                        editReminder = onEditReminder,
                        deleteReminder = onDeleteReminder
                    )
                }
                item {
                    AddReminder(
                        modifier = Modifier.padding(bottom = 24.dp),
                        onAddReminderClicked = onAddLastReminder
                    )
                }
                item {
                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(id = R.string.save_reminder_list),
                        onClick = onSaveReminderClicked,
                        isVisible = screenState.showSaveButton
                    )
                }
            }

            if (screenState.showCreateReminderDialog) {
                AddReminderDialog(
                    onDismiss = onDismissCreateDialogClicked,
                    focusRequester = FocusRequester(),
                    item = screenState.reminderToEdit,
                    onFirsReminderAdded = onFirstReminderAdded,
                    onLastReminderAdded = onLastReminderAdded,
                    isFirstReminder = screenState.isFirstReminder,
                    onReminderEdited = onReminderEdited
                )
            }
            if (screenState.showEmptyTitleDialog) {
                InformativeDialog(
                    onDismiss = onDismissEmptyTitleClicked,
                    titleId = R.string.warning,
                    explanationId = R.string.empty_title_explanation
                )
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
                remindersList = mutableListOf(
                    "Hello", "Hello2"
                )
            ),
            onReminderListTitleUpdate = {},
            onAddFirstReminder = {},
            onFirstReminderAdded = {},
            onAddLastReminder = {},
            onLastReminderAdded = {},
            onDismissCreateDialogClicked = {},
            onDismissEmptyTitleClicked = {},
            onSaveReminderClicked = {},
            onDeleteReminder = {},
            onEditReminder = {},
            onReminderEdited = {}
        )
    }
}

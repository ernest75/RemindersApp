package com.ernestschcneider.feature.reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.feature.reminders.data.models.Reminder
import com.ernestschcneider.feature.reminders.data.models.ReminderType
import com.ernestschcneider.feature.reminders.views.ReminderCreationDialog
import com.ernestschcneider.feature.reminders.views.ReminderListItem
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.composables.FloatingActionExtendedButton
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun RemindersScreen(
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onItemClicked: (reminderId: String) -> Unit,
    onNoteCreationClick: () -> Unit
) {
    val state by remindersViewModel.screenState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        remindersViewModel.loadReminders()
    }
    RemindersScreenContent(
        onNavigateUp = onNavigateUp,
        onItemClicked = onItemClicked,
        screenState = state,
        onDeleteItemClicked = remindersViewModel::removeItem,
        onAddButtonClicked = remindersViewModel::onAddButtonClicked,
        onDismissDialog = remindersViewModel::onDismissDialog,
        onNoteCreationClick = {
            onNoteCreationClick()
            remindersViewModel.onDismissDialog()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RemindersScreenContent(
    screenState: RemindersScreenState,
    onItemClicked: (reminderId: String) -> Unit,
    onNavigateUp: () -> Unit,
    onDeleteItemClicked: (Reminder) -> Unit,
    onAddButtonClicked: () -> Unit,
    onDismissDialog: () -> Unit,
    onNoteCreationClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = { FloatingActionExtendedButton(
            modifier = Modifier
                .padding(bottom = 24.dp),
            label = stringResource(id = R.string.add_reminder),
            onClick = onAddButtonClicked
        )},
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(4.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = AppTheme.colorScheme.onBackGround,
                        style = AppTheme.typography.titleNormal
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.colorScheme.surfaceContainerHigh
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.Close, contentDescription = stringResource(
                                id = R.string.close_icon
                            )
                        )
                    }
                },

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.surfaceContainerHigh)
                .padding(paddingValues)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(screenState.reminders) { item ->
                    when (item.reminderType) {
                        ReminderType.Note -> ReminderListItem(
                            item = item,
                            startDrawableRes = R.drawable.ic_note_24,
                            onItemClicked = onItemClicked,
                            onDeleteItemClicked = onDeleteItemClicked
                        )

                        ReminderType.List -> ReminderListItem(
                            item = item,
                            onItemClicked = onItemClicked,
                            startDrawableRes = R.drawable.ic_list_bulleted_24,
                            onDeleteItemClicked = onDeleteItemClicked
                        )
                    }
                }
            }
            if (screenState.showCreationDialog){
                ReminderCreationDialog(
                    onDismiss = onDismissDialog,
                    onCreateNote = onNoteCreationClick,
                    onCreateListNotes = {}
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun RemaindersScreenPreview() {
    AppTheme {
        RemindersScreenContent(
            onNavigateUp = {},
            onItemClicked = {},
            screenState = RemindersScreenState(),
            onDeleteItemClicked = {},
            onAddButtonClicked = {},
            onDismissDialog = {},
            onNoteCreationClick = {}
        )
    }
}

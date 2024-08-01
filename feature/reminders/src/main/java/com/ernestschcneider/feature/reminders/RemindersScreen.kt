package com.ernestschcneider.feature.reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun RemindersScreenContainer(
    onNavigateUp: () -> Unit,
    onItemClicked: (reminderId: String) -> Unit
) {
    RemindersScreen(
        onNavigateUp = onNavigateUp,
        onItemClicked = onItemClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RemindersScreen(
    onItemClicked: (reminderId: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
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
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.surfaceContainerHigh)
                .padding(paddingValues)
        ) {
            // TODO move this to a separate file
            val reminderNote = RemindersScreenState.Reminder(
                title = "GGGGGGGGGG",
                type = ReminderType.Note,
                id = "1"
            )
            val reminderList = RemindersScreenState.Reminder(
                title = "ddd",
                type = ReminderType.List,
                id = "2"
            )
            val reminderNote2 = RemindersScreenState.Reminder(
                title = "dfd",
                type = ReminderType.Note,
                id = "1"
            )
            val reminderList2 = RemindersScreenState.Reminder(
                title = "G",
                type = ReminderType.List,
                id = "2"
            )
            val reminderNote3 = RemindersScreenState.Reminder(
                title = "GGGGGGGGGG",
                type = ReminderType.Note,
                id = "1"
            )
            val reminderList3 = RemindersScreenState.Reminder(
                title = "ddd",
                type = ReminderType.List,
                id = "2"
            )
            val reminderNote4 = RemindersScreenState.Reminder(
                title = "dfd",
                type = ReminderType.Note,
                id = "1"
            )
            val reminderList4 = RemindersScreenState.Reminder(
                title = "G",
                type = ReminderType.List,
                id = "2"
            )
            val items = listOf(
                reminderNote,
                reminderList,
                reminderList2,
                reminderList3,
                reminderNote2,
                reminderNote3,
                reminderNote4,
                reminderList4
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items) { item ->
                    when (item.type) {
                        ReminderType.Note -> ReminderListItem(
                            item = item,
                            startDrawableRes = R.drawable.ic_note_24,
                            onItemClicked = onItemClicked
                        )

                        ReminderType.List -> ReminderListItem(
                            item = item,
                            startDrawableRes = R.drawable.ic_list_bulleted_24,
                            onItemClicked = onItemClicked
                        )
                    }


                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun RemaindersScreenPreview() {
    AppTheme {
        RemindersScreen(onNavigateUp = {}, onItemClicked = {})
    }
}
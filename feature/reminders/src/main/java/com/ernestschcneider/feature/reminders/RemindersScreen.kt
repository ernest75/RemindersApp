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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun RemindersScreen(
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onItemClicked: (reminderId: String) -> Unit
) {
    val state by remindersViewModel.screenState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        remindersViewModel.loadRemindersDetails()
    }
    RemindersScreenContent(
        onNavigateUp = onNavigateUp,
        onItemClicked = onItemClicked,
        screenState = state,
        onDeleteItemClicked = remindersViewModel::removeItem
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RemindersScreenContent(
    screenState: RemindersScreenState,
    onItemClicked: (reminderId: String) -> Unit,
    onNavigateUp: () -> Unit,
    onDeleteItemClicked: (Reminder) -> Unit
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(screenState.reminders) { item ->
                    when (item.type) {
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
            onDeleteItemClicked = {}
        )
    }
}

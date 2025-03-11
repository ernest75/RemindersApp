package com.ernestschcneider.feature.reminders

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.feature.reminders.views.ReminderCreationDialog
import com.ernestschcneider.feature.reminders.views.RemindersItem
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.core.states.rememberDragAndDropListState
import com.ernestschcneider.remindersapp.core.testtags.*
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.composables.FloatingActionButton
import com.ernestschcneider.remindersapp.core.view.composables.isScrollingUp
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
internal fun RemindersScreen(
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onReminderClicked: (reminderId: String) -> Unit,
    onListReminderClick: (reminderId: String) -> Unit,
    onReminderCreationClick: () -> Unit,
    onListReminderCreationClick: () -> Unit
) {
    val state by remindersViewModel.screenState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        remindersViewModel.loadReminders()
    }
    RemindersScreenContent(
        onNavigateUp = onNavigateUp,
        onReminderClicked = onReminderClicked,
        screenState = state,
        onDeleteItemClicked = remindersViewModel::removeItem,
        onAddButtonClicked = remindersViewModel::onAddButtonClicked,
        onDismissDialog = remindersViewModel::onDismissDialog,
        onReminderCreationClick = {
            onReminderCreationClick()
            remindersViewModel.onDismissDialog()
        },
        onListReminderCreationClick = {
            onListReminderCreationClick()
            remindersViewModel.onDismissDialog()
        },
        onListReminderClick = onListReminderClick,
        onMoveReminder = remindersViewModel::onMoveReminder,
        onReminderMoved = remindersViewModel::onReminderMoved
    )
}

@SuppressLint("UnnecessaryComposedModifier")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RemindersScreenContent(
    screenState: RemindersScreenState,
    onNavigateUp: () -> Unit,
    onDeleteItemClicked: (Reminder) -> Unit,
    onAddButtonClicked: () -> Unit,
    onDismissDialog: () -> Unit,
    onReminderClicked: (reminderId: String) -> Unit,
    onReminderCreationClick: () -> Unit,
    onListReminderClick: (String) -> Unit,
    onListReminderCreationClick: () -> Unit,
    onMoveReminder: (Int, Int) -> Unit,
    onReminderMoved: () -> Unit
) {
    val listState = rememberLazyListState()
    var overscrollJob by remember { mutableStateOf<Job?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .testTag("addButton"),
                label = stringResource(id = R.string.add_reminder),
                onClick = onAddButtonClicked,
                isExpanded = listState.isScrollingUp()
            )
        },
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
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(
                                id = R.string.close_icon
                            ),
                            tint = AppTheme.colorScheme.contentTint
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
            val dragAndDropListState =
                rememberDragAndDropListState(listState) { from, to ->
                    onMoveReminder(from, to)
                }
            val coroutineScope = rememberCoroutineScope()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress(
                            onDrag = { change, offset ->
                                change.consume()
                                dragAndDropListState.onDrag(offset)

                                if (overscrollJob?.isActive == true) return@detectDragGesturesAfterLongPress

                                dragAndDropListState
                                    .checkOverscroll()
                                    .takeIf { it != 0f }
                                    ?.let {
                                        overscrollJob = coroutineScope.launch {
                                            dragAndDropListState.lazyListState.scrollBy(it)
                                        }
                                    } ?: kotlin.run { overscrollJob?.cancel() }

                            },
                            onDragStart = { offset ->
                                dragAndDropListState.onDragStart(offset)
                            },
                            onDragEnd = {
                                dragAndDropListState.onDragInterrupted()
                                onReminderMoved()
                            },
                            onDragCancel = { dragAndDropListState.onDragInterrupted() }
                        )
                    },
                verticalArrangement = Arrangement.spacedBy(16.dp),
                state = listState
            ) {
                itemsIndexed(screenState.reminders) { index, item ->
                    when (item.reminderType) {
                        ReminderType.Note -> RemindersItem(
                            item = item,
                            modifier = Modifier.composed {
                                val offsetOrNull =
                                    dragAndDropListState.elementDisplacement.takeIf {
                                        index == dragAndDropListState.currentIndexOfDraggedItem
                                    }
                                Modifier.graphicsLayer {
                                    translationY = offsetOrNull ?: 0f
                                }
                            },
                            startDrawableRes = R.drawable.ic_note_24,
                            onItemClicked = onReminderClicked,
                            onDeleteItemClicked = onDeleteItemClicked
                        )

                        ReminderType.List -> RemindersItem(
                            item = item,
                            modifier = Modifier.composed {
                                val offsetOrNull =
                                    dragAndDropListState.elementDisplacement.takeIf {
                                        index == dragAndDropListState.currentIndexOfDraggedItem
                                    }
                                Modifier.graphicsLayer {
                                    translationY = offsetOrNull ?: 0f
                                }
                            },
                            onItemClicked = onListReminderClick,
                            startDrawableRes = R.drawable.ic_list_bulleted_24,
                            onDeleteItemClicked = onDeleteItemClicked
                        )
                    }
                }
            }
            if (screenState.showCreationDialog) {
                ReminderCreationDialog(
                    onDismiss = onDismissDialog,
                    onCreateReminder = onReminderCreationClick,
                    onCreateListReminder = onListReminderCreationClick
                )
            }
            if (screenState.showLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(REMINDERS_LOADING),
                    color = AppTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun RemaindersScreenEmptyPreview() {
    AppTheme {
        RemindersScreenContent(
            onNavigateUp = {},
            onReminderClicked = {},
            screenState = RemindersScreenState(showLoading = true),
            onDeleteItemClicked = {},
            onAddButtonClicked = {},
            onDismissDialog = {},
            onReminderCreationClick = {},
            onListReminderCreationClick = {},
            onListReminderClick = {},
            onMoveReminder = { _, _ -> },
            onReminderMoved = {}
        )
    }
}

@Composable
@PreviewLightDark
private fun RemaindersScreenNotEmptyPreview() {
    AppTheme {
        RemindersScreenContent(
            onNavigateUp = {},
            onReminderClicked = {},
            screenState = RemindersScreenState(reminders = listOf(Reminder(reminderTitle = "Title1"))),
            onDeleteItemClicked = {},
            onAddButtonClicked = {},
            onDismissDialog = {},
            onReminderCreationClick = {},
            onListReminderCreationClick = {},
            onListReminderClick = {},
            onMoveReminder = { _, _ -> },
            onReminderMoved = {}
        )
    }
}

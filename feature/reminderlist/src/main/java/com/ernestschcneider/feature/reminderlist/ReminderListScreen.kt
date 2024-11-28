package com.ernestschcneider.feature.reminderlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.feature.reminderlist.views.AddReminder
import com.ernestschcneider.feature.reminderlist.views.AddReminderDialog
import com.ernestschcneider.feature.reminderlist.views.RemindersListItem
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.remindersapp.core.states.rememberDragAndDropListState
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.composables.InformativeDialog
import com.ernestschcneider.remindersapp.core.view.composables.PrimaryButton
import com.ernestschcneider.remindersapp.core.view.composables.RemindersTopAppBar
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
        onReminderEdited = reminderListViewModel::onReminderEdited,
        onMoveListItem = reminderListViewModel::onMoveListItem,
        onCrossReminder = reminderListViewModel::onCrossReminder,
        onDragFinished = reminderListViewModel::onDragFinished
    )
}

@SuppressLint("UnnecessaryComposedModifier")
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
    onDeleteReminder: (ReminderListItem) -> Unit,
    onEditReminder: (ReminderListItem) -> Unit,
    onReminderEdited: (ReminderListItem) -> Unit,
    onMoveListItem: (Int, Int) -> Unit,
    onCrossReminder: (ReminderListItem) -> Unit,
    onDragFinished: () -> Unit
) {
    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }
    var overscrollJob by remember { mutableStateOf<Job?>(null) }

    if (screenState.scrollListToLast) {
        LaunchedEffect(Unit) {
            listState.scrollToItem(screenState.remindersList.size)
        }
    }
    if (screenState.requestFocus) {
        focusRequester.requestFocus()
    }
    Scaffold(modifier = Modifier
        .padding(top = 48.dp)
        .fillMaxSize()
        .testTag("reminderList"),
        topBar = {
        RemindersTopAppBar(
            onNavigateUp = onNavigateUp,
            onTitleUpdate = onReminderListTitleUpdate,
            focusRequester = focusRequester,
            value = screenState.reminderListTitle,
            titlePlaceHolderId = R.string.type_reminder_title,
            parentScreenHasMoreFocusableElements = false
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.surfaceContainerHigh)
                .padding(paddingValues)
        ) {
            val dragAndDropListState =
                rememberDragAndDropListState(listState, onDragFinished) { from, to ->
                    onMoveListItem(from, to)
                }
            val coroutineScope = rememberCoroutineScope()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
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
                            },
                            onDragCancel = { dragAndDropListState.onDragInterrupted() }
                        )
                    },
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = listState
            ) {
                item {
                    AddReminder(
                        modifier = Modifier.padding(top = 24.dp),
                        onAddReminderClicked = onAddFirstReminder
                    )
                }
                itemsIndexed(screenState.remindersList) { index, item ->
                    RemindersListItem(
                        item = item,
                        modifier = Modifier.composed {
                            val offsetOrNull =
                                dragAndDropListState.elementDisplacement.takeIf {
                                    index + 1 == dragAndDropListState.currentIndexOfDraggedItem
                                }
                            Modifier.graphicsLayer {
                                translationY = offsetOrNull ?: 0f
                            }
                        },
                        editReminder = onEditReminder,
                        deleteReminder = onDeleteReminder,
                        crossReminder = onCrossReminder
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
                        label = stringResource(id = R.string.save_changes),
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
                remindersList = mutableListOf()
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
            onReminderEdited = {},
            onMoveListItem = { _, _ -> },
            onCrossReminder = {},
            onDragFinished = {}
        )
    }
}

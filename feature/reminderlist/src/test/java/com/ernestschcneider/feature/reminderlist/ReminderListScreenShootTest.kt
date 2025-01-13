package com.ernestschcneider.feature.reminderlist

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class ReminderListScreenShootTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun emptyListNoTitle() {
        paparazzi.snapshot {
            AppTheme {
                ReminderListScreenContent(
                    onNavigateUp = {},
                    screenState = ReminderListState(
                        remindersList = listOf()
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
    }

    @Test
    fun emptyListTitle() {
        paparazzi.snapshot {
            AppTheme {
                ReminderListScreenContent(
                    onNavigateUp = {},
                    screenState = ReminderListState(
                        remindersList = listOf(),
                        reminderListTitle = "Title"
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
    }

    @Test
    fun listOfOneNoTitle() {
        paparazzi.snapshot {
            val item = ReminderListItem(position = 0, text = "Element1")
            AppTheme {
                ReminderListScreenContent(
                    onNavigateUp = {},
                    screenState = ReminderListState(
                        remindersList = listOf(item)
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
    }

    @Test
    fun listOfOneTitle() {
        paparazzi.snapshot {
            val item = ReminderListItem(position = 0, text = "Element1")
            AppTheme {
                ReminderListScreenContent(
                    onNavigateUp = {},
                    screenState = ReminderListState(
                        reminderListTitle = "Title",
                        remindersList = listOf(item)
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
    }

    @Test
    fun listOfManyNoTitle() {
        paparazzi.snapshot {
           val list = mutableListOf<ReminderListItem>()
           for (i in 0 until 5) {
                list.add(ReminderListItem(position = i, text = "Element$i" ))
            }
            AppTheme {
                ReminderListScreenContent(
                    onNavigateUp = {},
                    screenState = ReminderListState(
                        remindersList = list
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
    }

    @Test
    fun listOfManyTitle() {
        paparazzi.snapshot {
            val list = mutableListOf<ReminderListItem>()
            for (i in 0 until 5) {
                list.add(ReminderListItem(position = i, text = "Element$i" ))
            }
            AppTheme {
                ReminderListScreenContent(
                    onNavigateUp = {},
                    screenState = ReminderListState(
                        reminderListTitle = "Title",
                        remindersList = list
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
    }

    @Test
    fun showSaveButton() {
        paparazzi.snapshot {
            AppTheme {
                ReminderListScreenContent(
                    onNavigateUp = {},
                    screenState = ReminderListState(
                        showSaveButton = true
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
    }
}

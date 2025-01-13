package com.ernestschneider.feature.reminders

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.ernestschcneider.feature.reminders.RemindersScreenContent
import com.ernestschcneider.feature.reminders.RemindersScreenState
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschneider.testutils.ReminderBuilder
import org.junit.Rule
import org.junit.Test

class RemindersScreenShootTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun emptyState() {
        paparazzi.snapshot {
            AppTheme {
                RemindersScreenContent(
                    onNavigateUp = {},
                    onReminderClicked = {},
                    screenState = RemindersScreenState(),
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
    }

    @Test
    fun singleReminderNote() {
        paparazzi.snapshot {
            val reminder = ReminderBuilder.aReminder().withReminderType(ReminderType.Note).build()
            AppTheme {
                RemindersScreenContent(
                    onNavigateUp = {},
                    onReminderClicked = {},
                    screenState = RemindersScreenState(reminders = listOf(reminder)),
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
    }

    @Test
    fun singleReminderList() {
        paparazzi.snapshot {
            val reminder = ReminderBuilder.aReminder().withReminderType(ReminderType.List).build()
            AppTheme {
                RemindersScreenContent(
                    onNavigateUp = {},
                    onReminderClicked = {},
                    screenState = RemindersScreenState(reminders = listOf(reminder)),
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
    }

    @Test
    fun multipleReminders() {
        paparazzi.snapshot {
            val reminderList = listOf(
                ReminderBuilder
                    .aReminder()
                    .withReminderType(ReminderType.List)
                    .build(),
                ReminderBuilder
                    .aReminder()
                    .withReminderType(ReminderType.Note)
                    .build()
            )
            AppTheme {
                RemindersScreenContent(
                    onNavigateUp = {},
                    onReminderClicked = {},
                    screenState = RemindersScreenState(reminders = reminderList),
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
    }
}
package com.ernestschneider.feature.remindercereration.notecreation

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.ernestschcneider.feature.remindernote.ReminderNoteScreenContent
import com.ernestschcneider.feature.remindernote.ReminderNoteState
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class ReminderNoteScreenShootTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun noteWithTitleNotContent() {
        paparazzi.snapshot {
            AppTheme {
                ReminderNoteScreenContent(
                    onNavigateUp = {},
                    onReminderNoteContentUpdate = {},
                    state = ReminderNoteState(reminderTitle = TextFieldValue("Title")),
                    onReminderNoteSaved = {},
                    onReminderNoteTitleUpdate = {},
                    onDismissEmptyTitleDialog = {}
                )
            }
        }
    }

    @Test
    fun noteWithTitleAndContent() {
        paparazzi.snapshot {
            AppTheme {
                ReminderNoteScreenContent(
                    onNavigateUp = {},
                    onReminderNoteContentUpdate = {},
                    state = ReminderNoteState(
                        reminderTitle = TextFieldValue("Title"),
                        reminderContent = "Content"
                    ),
                    onReminderNoteSaved = {},
                    onReminderNoteTitleUpdate = {},
                    onDismissEmptyTitleDialog = {}
                )
            }
        }
    }

    @Test
    fun showingSaveButton() {
        paparazzi.snapshot {
            AppTheme {
                ReminderNoteScreenContent(
                    onNavigateUp = {},
                    onReminderNoteContentUpdate = {},
                    state = ReminderNoteState(
                        reminderTitle = TextFieldValue("Title"),
                        reminderContent = "Content",
                        showSaveButton = true,
                    ),
                    onReminderNoteSaved = {},
                    onReminderNoteTitleUpdate = {},
                    onDismissEmptyTitleDialog = {}
                )
            }
        }
    }

    @Test
    fun showingEmptyDialog() {
        paparazzi.snapshot {
            AppTheme {
                ReminderNoteScreenContent(
                    onNavigateUp = {},
                    onReminderNoteContentUpdate = {},
                    state = ReminderNoteState(
                        reminderContent = "Content",
                        showSaveButton = true,
                        showEmptyTitleDialog = true
                    ),
                    onReminderNoteSaved = {},
                    onReminderNoteTitleUpdate = {},
                    onDismissEmptyTitleDialog = {}
                )
            }
        }
    }
}

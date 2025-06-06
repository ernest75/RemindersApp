package com.ernestschcneider.remindersapp.remindernote

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.input.TextFieldValue
import com.ernestschcneider.remindersapp.models.ReminderType
import com.ernestschcneider.remindersapp.MainActivity
import com.ernestschcneider.remindersapp.core.testtags.REMINDER_NOTE_TITLE
import com.ernestschcneider.remindersapp.data.local.repo.LocalModule
import com.ernestschcneider.remindersapp.local.LocalRepo
import com.ernestschneider.testutils.InMemoryLocalRepo
import com.ernestschneider.testutils.ReminderBuilder
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(LocalModule::class)
class ReminderNoteTest {

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val reminderNoteTestRule = createAndroidComposeRule<MainActivity>()

    private val reminderNote = ReminderBuilder.aReminder()
        .withId("1")
        .withReminderTitle(TextFieldValue(REMINDER_NOTE_TITLE))
        .withReminderContent("Content1")
        .withReminderType(ReminderType.Note)
        .withReminderPosition(0)
        .build()

    private val remindersList = mutableListOf(reminderNote)

    @BindValue
    @JvmField
    val localRepo: LocalRepo = InMemoryLocalRepo(remindersList)

    @Test
    fun addTitle() {
        launchReminderNoteScreenWithoutNote(reminderNoteTestRule) {
            clickAddTitle()
        } verify {
            reminderNoteTitleIsShown()
            reminderNoteSaveButtonIsShown()
        }
    }

    @Test
    fun addNote() {
        launchReminderNoteScreenWithoutNote(reminderNoteTestRule) {
            clickAddNote()
        } verify {
            reminderNoteIsShown()
            reminderNoteSaveButtonIsShown()
        }
    }

    @Test
    fun clickSaveButtonOnEmptyTitle() {
        launchReminderNoteScreenWithoutNote(reminderNoteTestRule) {
            clickOnSaveButton()
        } verify {
            reminderNoteNoTitleDialogIsShown()
        }
    }

    @Test
    fun clickSaveButtonNotEmptyTitle() {
        launchReminderNoteScreenWithoutNote(reminderNoteTestRule) {
            clickAddTitle()
            clickOnSaveButton()
        } verify {
            reminderNoteNoTitleDialogIsNotShown()
        }
    }

    @Test
    fun clickSaveButtonNotEmptyNoteEmptyTitle() {
        launchReminderNoteScreenWithoutNote(reminderNoteTestRule) {
            clickAddNote()
            clickOnSaveButton()
        } verify {
            reminderNoteNoTitleDialogIsShown()
        }
    }

    @Test
    fun existingReminderNote() {
        launchReminderNoteScreenWithNote(reminderNoteTestRule) {
            /// No Action
        } verify {
            existingReminderCorrectFieldsShown(reminderNote)
        }
    }

    @Test
    fun modifyReminderNoteTitle() {
        launchReminderNoteScreenWithNote(reminderNoteTestRule) {
            clickModifyTitle()
        } verify {
            reminderNoteModifiedTitleIsShown()
            reminderNoteSaveButtonIsShown()
        }
    }

    @Test
    fun modifyReminderNoteContent() {
        launchReminderNoteScreenWithNote(reminderNoteTestRule) {
            clickAddNote()
        } verify {
            reminderNoteModifiedContentIsShown(reminderNote.reminderContent)
            reminderNoteSaveButtonIsShown()
        }
    }
}

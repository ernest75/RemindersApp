package com.ernestschcneider.remindersapp.remindernote

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.MainActivity
import com.ernestschcneider.remindersapp.local.HiltWrapper_LocalModule
import com.ernestschcneider.remindersapp.local.StorageRepo
import com.ernestschneider.testutils.InMemoryLocalRepo
import com.ernestschneider.testutils.ReminderBuilder
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(HiltWrapper_LocalModule::class)
class ReminderNoteTest {

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val reminderNoteTestRule = createAndroidComposeRule<MainActivity>()

    private val reminderNote = ReminderBuilder.aReminder()
        .withId("1")
        .withReminderTitle("Title1")
        .withReminderContent("Content1")
        .withReminderType(ReminderType.Note)
        .build()

    private val remindersList = mutableListOf(reminderNote)

    @BindValue
    @JvmField
    val localRepo: StorageRepo = InMemoryLocalRepo(remindersList)

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
}

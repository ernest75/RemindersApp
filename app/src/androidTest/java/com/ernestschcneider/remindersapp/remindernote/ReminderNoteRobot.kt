package com.ernestschcneider.remindersapp.remindernote

import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ernestschcneider.remindersapp.MainActivity
import com.ernestschcneider.remindersapp.core.testtags.*
import com.ernestschcneider.remindersapp.reminders.launchRemindersScreen


fun launchReminderNoteScreenWithoutNote(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: ReminderNoteRobot.() -> Unit
): ReminderNoteRobot {
    launchRemindersScreen(rule) {
        clickAddButton()
        clickAddReminderNoteButton()
    }
  return ReminderNoteRobot(rule).apply(block)
}


class ReminderNoteRobot(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    infix fun verify(
        block: ReminderNoteVerification.() -> Unit
    ): ReminderNoteVerification {
        return ReminderNoteVerification(rule).apply(block)
    }

    fun clickAddTitle() {
        rule.onNodeWithTag(REMINDER_NOTE_TOP_BAR)
            .performClick()
        rule.onNodeWithTag(TEXT_INPUT_TOP_BAR)
            .performTextInput(TEX_TEST)
    }

    fun clickAddNote() {
        rule.onNodeWithTag(REMINDER_NOTE_TEXT_FIELD)
            .performTextInput(TEX_TEST)
    }

    fun clickOnSaveButton() {
        rule.onNodeWithTag(REMINDER_NOTE_SAVE_BUTTON)
            .performClick()
    }
}

class ReminderNoteVerification(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    fun reminderNoteTitleIsShown() {
        rule.onNodeWithText(TEX_TEST)
            .assertIsDisplayed()
    }

    fun reminderNoteIsShown() {
        rule.onNodeWithText(TEX_TEST)
            .assertIsDisplayed()
    }

    fun reminderNoteSaveButtonIsShown() {
        rule.onNodeWithTag(REMINDER_NOTE_SAVE_BUTTON)
            .assertIsDisplayed()
    }

    fun reminderNoteNoTitleDialogIsShown() {
        rule.onNodeWithTag(INFORMATIVE_DIALOG)
            .assertIsDisplayed()
    }

    fun reminderNoteNoTitleDialogIsNotShown() {
        rule.onNodeWithTag(INFORMATIVE_DIALOG)
            .assertIsNotDisplayed()
    }
}

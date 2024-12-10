package com.ernestschcneider.remindersapp.reminders

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ernestschcneider.remindersapp.core.testtags.*
import com.ernestschcneider.remindersapp.MainActivity


fun launchRemindersScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: RemindersRobot.() -> Unit
): RemindersRobot {
    return RemindersRobot(rule).apply(block)
}

class RemindersRobot(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    fun clickAddButton() {
        rule.onNodeWithTag(ADD_REMINDER_BUTTON)
            .performClick()
    }

    infix fun verify(
        block: RemindersVerification.() -> Unit
    ): RemindersVerification {
        return RemindersVerification(rule).apply(block)
    }

    fun clickAddReminderNoteButton() {
        rule.onNodeWithTag(ADD_NOTE_BUTTON)
            .performClick()
    }

    fun clickAddReminderListButton() {
        rule.onNodeWithTag(ADD_LIST_BUTTON)
            .performClick()
    }

    fun clickReminderNote() {
        rule.onNodeWithText(REMINDER_NOTE_TITLE)
            .performClick()
    }

    fun clickReminderList() {
        rule.onNodeWithText(REMINDER_LIST_TITLE)
            .performClick()
    }

    fun clickDeleteRemindersItem(reminderId: String) {
        rule.onNodeWithTag(REMINDERS_ITEM_DELETE_ICON + reminderId)
            .performClick()
    }

}




class RemindersVerification(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun createReminderDialogIsShown() {
        rule.onNodeWithTag(REMINDER_CREATION_DIALOG)
            .assertIsDisplayed()
    }

    fun reminderNoteIsShown() {
        rule.onNodeWithTag(REMINDER_NOTE)
            .assertIsDisplayed()
    }

    fun reminderListIsShown() {
        rule.onNodeWithTag(REMINDER_LIST)
            .assertIsDisplayed()
    }

    fun reminderNoteElementIsShown() {
        rule.onNodeWithText(REMINDER_NOTE_TITLE)
            .assertIsDisplayed()
    }

    fun reminderListElementIsShown() {
        rule.onNodeWithText(REMINDER_LIST_TITLE)
            .assertIsDisplayed()
    }

    fun reminderNoteIsNotShown() {
        rule.onNodeWithText(REMINDER_NOTE_TITLE)
            .assertIsNotDisplayed()
    }

    fun reminderListIsNotShown() {
        rule.onNodeWithText(REMINDER_LIST_TITLE)
            .assertIsNotDisplayed()
    }
}
package com.ernestschcneider.remindersapp

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ernestschcneider.remindersapp.core.view.R.*

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
        rule.onNodeWithTag("addButton")
            .performClick()
    }

    infix fun verify(
        block: RemindersVerification.() -> Unit
    ): RemindersVerification {
        return RemindersVerification(rule).apply(block)
    }

    fun clickAddReminderNoteButton() {
        rule.onNodeWithTag("addNoteButton")
            .performClick()
    }

    fun clickAddReminderListButton() {
        rule.onNodeWithTag("addListButton")
            .performClick()
    }

    fun clickReminderNote() {
        rule.onNodeWithText("ReminderNoteTitle")
            .performClick()
    }

    fun clickReminderList() {
        rule.onNodeWithText("ReminderListTitle")
            .performClick()
    }

    // TODO try to fix this, how to access to children's of lazylist custom actions
    fun clickDeleteReminderNote() {
//        val deleteIconContentDescription = rule.activity.getString(string.delete_icon)
//        rule.onNodeWithContentDescription(deleteIconContentDescription).performClick()
     //   rule.onNodeWithTag("remindersList").performScrollToNode().on
    }

}

class RemindersVerification(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun createReminderDialogIsShown() {
        rule.onNodeWithTag("reminderCreationDialog")
            .assertIsDisplayed()
    }

    fun reminderNoteIsShown() {
        rule.onNodeWithTag("reminderNote")
            .assertIsDisplayed()
    }

    fun reminderListIsShown() {
        rule.onNodeWithTag("reminderList")
            .assertIsDisplayed()
    }

    fun reminderNoteElementIsShown() {
        rule.onNodeWithText("ReminderNoteTitle")
            .assertIsDisplayed()
    }

    fun reminderListElementIsShown() {
        rule.onNodeWithText("ReminderListTitle")
            .assertIsDisplayed()
    }
}
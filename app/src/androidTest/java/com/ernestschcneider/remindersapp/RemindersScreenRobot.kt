package com.ernestschcneider.remindersapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule

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

}

class RemindersVerification(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun createReminderDialogIsShown() {
        rule.onNodeWithTag("reminderCreationDialog")
            .assertIsDisplayed()
    }
}
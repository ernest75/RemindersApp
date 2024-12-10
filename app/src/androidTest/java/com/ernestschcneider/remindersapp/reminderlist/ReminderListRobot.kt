package com.ernestschcneider.remindersapp.reminderlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ernestschcneider.remindersapp.MainActivity
import com.ernestschcneider.remindersapp.core.testtags.*
import com.ernestschcneider.remindersapp.reminders.launchRemindersScreen


fun launchReminderListScreenWithoutReminder(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: ReminderListRobot.() -> Unit
): ReminderListRobot {
    launchRemindersScreen(rule) {
        clickAddButton()
        clickAddReminderListButton()
    }
    return ReminderListRobot(rule).apply(block)
}

class ReminderListRobot(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    infix fun verify(
        block: ReminderListVerification.() -> Unit
    ): ReminderListVerification {
        return ReminderListVerification(rule).apply(block)
    }

    fun addTitle() {
        rule.onNodeWithTag(TEXT_INPUT_TOP_BAR)
            .performTextInput(TEX_TEST)
    }
}

class ReminderListVerification(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun saveButtonNotShown() {
        rule.onNodeWithTag(REMINDER_LIST_SAVE_BUTTON)
            .assertIsNotDisplayed()
    }

    fun saveButtonShown() {
        rule.onNodeWithTag(REMINDER_LIST_SAVE_BUTTON)
            .assertIsDisplayed()
    }

}

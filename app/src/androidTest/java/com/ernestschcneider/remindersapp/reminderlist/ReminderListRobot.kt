package com.ernestschcneider.remindersapp.reminderlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderListItem
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

fun launchReminderListScreenWithReminder(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: ReminderListRobot.() -> Unit
): ReminderListRobot {
    launchRemindersScreen(rule) {
        clickReminderList()
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
            .performTextInput(TEX_INPUT_FOR_TEST)
    }

    fun addFirstElement() {
        rule.onNodeWithTag(ADD_FIRST_ON_REMINDER_LIST_BUTTON)
            .performClick()
        rule.onNodeWithTag(ADD_REMINDER_DIALOG_TEXT_FIELD)
            .performTextInput(TEX_INPUT_FOR_TEST)
        rule.onNodeWithTag(ADD_REMINDER_DIALOG_OK_BUTTON)
            .performClick()
    }

    fun addLastElement() {
        rule.onNodeWithTag(ADD_LAST_ON_REMINDER_LIST_BUTTON)
            .performClick()
        rule.onNodeWithTag(ADD_REMINDER_DIALOG_TEXT_FIELD)
            .performTextInput(TEX_INPUT_FOR_TEST)
        rule.onNodeWithTag(ADD_REMINDER_DIALOG_OK_BUTTON)
            .performClick()
    }

    fun cancelAddingElement() {
        rule.onNodeWithTag(ADD_FIRST_ON_REMINDER_LIST_BUTTON)
            .performClick()
        rule.onNodeWithTag(ADD_REMINDER_DIALOG_TEXT_FIELD)
            .performTextInput(TEX_INPUT_FOR_TEST)
        rule.onNodeWithTag(ADD_REMINDER_DIALOG_CANCEL_BUTTON)
            .performClick()
    }

    fun deleteElement(position: Int) {
        rule.onNodeWithTag(REMINDERS_ITEM_DELETE_ICON + position)
            .performClick()
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

    fun correctFieldsAreShownForExistingReminderList(reminder: Reminder) {
        rule.onNodeWithText(reminder.reminderTitle).assertIsDisplayed()
        correctListIsShown(reminder.remindersList)
    }

    fun addedElementIsShown() {
        rule.onNodeWithText(TEX_INPUT_FOR_TEST)
            .assertIsDisplayed()
    }

    private fun correctListIsShown(remindersList: ArrayList<ReminderListItem>) {
        remindersList.forEach {
            rule.onNodeWithText(it.text).assertIsDisplayed()
        }
    }

    fun noNewElementIsShown() {
        rule.onNodeWithText(TEX_INPUT_FOR_TEST)
            .assertIsNotDisplayed()
    }

    fun elementIsNotShown(deletedElement: ReminderListItem) {
        rule.onNodeWithText(deletedElement.text)
            .assertIsNotDisplayed()
    }
}

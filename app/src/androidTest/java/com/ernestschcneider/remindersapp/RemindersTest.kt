package com.ernestschcneider.remindersapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class RemindersTest {

    @get: Rule
    val remindersTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun clickOnCreateReminderShowsDialog() {
        launchRemindersScreen(remindersTestRule) {
            clickAddButton()
        } verify {
            createReminderDialogIsShown()
        }
    }
}


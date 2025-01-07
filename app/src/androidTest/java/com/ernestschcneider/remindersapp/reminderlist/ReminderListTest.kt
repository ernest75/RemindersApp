package com.ernestschcneider.remindersapp.reminderlist

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.MainActivity
import com.ernestschcneider.remindersapp.core.testtags.REMINDER_LIST_TITLE
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
class ReminderListTest {

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val remindersTestRule = createAndroidComposeRule<MainActivity>()

    private val reminderElementsList = arrayListOf(
        ReminderListItem(position = 0, text = "Element1"),
        ReminderListItem(position = 1, text = "Element2")
    )
    private val reminderList = ReminderBuilder.aReminder()
        .withId("2")
        .withReminderTitle(REMINDER_LIST_TITLE)
        .withReminderType(ReminderType.List)
        .withReminderPosition(1)
        .withReminderList(
            reminderElementsList
        ).build()

    private val remindersList = mutableListOf(reminderList)

    @BindValue
    @JvmField
    val localRepo: StorageRepo = InMemoryLocalRepo(remindersList)

    @Test
    fun onEmptyTitleNoteListSaveButtonNotShown() {
        launchReminderListScreenWithoutReminder(remindersTestRule) {
            // no action
        } verify {
            saveButtonNotShown()
        }
    }

    @Test
    fun onAddTitleSaveButtonShown() {
        launchReminderListScreenWithoutReminder(remindersTestRule) {
            addTitle()
        } verify {
            saveButtonShown()
        }
    }

    @Test
    fun existingReminderList() {
        launchReminderListScreenWithReminder(remindersTestRule) {
            // No action
        } verify {
            correctFieldsAreShownForExistingReminderList(reminderList)
            saveButtonNotShown()
        }
    }

    @Test
    fun onAddFirstListElement() {
        launchReminderListScreenWithReminder(remindersTestRule) {
            addFirstElement()
        } verify {
            addedElementIsShown()
            saveButtonShown()
        }
    }

    @Test
    fun onAddLastListElement() {
        launchReminderListScreenWithReminder(remindersTestRule) {
            addLastElement()
        } verify {
            addedElementIsShown()
            saveButtonShown()
        }
    }

    @Test
    fun onCancelingAddingElement() {
        launchReminderListScreenWithReminder(remindersTestRule) {
            cancelAddingElement()
        } verify {
            noNewElementIsShown()
        }
    }

    @Test
    fun deleteElement() {
        val deletedElement = reminderList.remindersList.first()
        launchReminderListScreenWithReminder(remindersTestRule) {
            deleteElement(deletedElement.position)
        } verify {
            elementIsNotShown(deletedElement)
        }
    }
}

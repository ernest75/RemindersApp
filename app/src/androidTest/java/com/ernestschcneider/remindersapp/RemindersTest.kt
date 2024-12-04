package com.ernestschcneider.remindersapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.models.ReminderType
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
class RemindersTest {

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val remindersTestRule = createAndroidComposeRule<MainActivity>()

    private val reminderNote = ReminderBuilder.aReminder()
        .withId("1")
        .withReminderTitle("ReminderNoteTitle")
        .withReminderContent("Content1")
        .withReminderType(ReminderType.Note)
        .build()

    private val reminderList = ReminderBuilder.aReminder()
        .withId("2")
        .withReminderTitle("ReminderListTitle")
        .withReminderType(ReminderType.List)
        .withReminderList(
            arrayListOf(
                ReminderListItem(position = 0, text = "Element1"),
                ReminderListItem(position = 1, text = "Element2")
            )
        ).build()

    private val remindersList = mutableListOf(reminderNote, reminderList)

    @BindValue
    @JvmField
    val localRepo: StorageRepo = InMemoryLocalRepo(remindersList)

    @Test
    fun clickOnCreateReminderShowsDialog() {
        launchRemindersScreen(remindersTestRule) {
            clickAddButton()
        } verify {
            createReminderDialogIsShown()
        }
    }

    @Test
    fun onAddReminderNoteAddsReminder() {
        launchRemindersScreen(remindersTestRule){
            clickAddButton()
            clickAddReminderNoteButton()
        } verify {
            reminderNoteIsShown()
        }
    }

    @Test
    fun onAddReminderListAddsReminderList() {
        launchRemindersScreen(remindersTestRule) {
            clickAddButton()
            clickAddReminderListButton()
        } verify {
            reminderListIsShown()
        }
    }

    @Test
    fun onListRemindersShown() {
        launchRemindersScreen(remindersTestRule) {
        // No operation
        } verify {
            reminderNoteElementIsShown()
            reminderListElementIsShown()
        }
    }

    @Test
    fun onReminderNoteClick() {
        launchRemindersScreen(remindersTestRule) {
            clickReminderNote()
        } verify {
            reminderNoteElementIsShown()
        }
    }

    @Test
    fun onReminderListClick() {
        launchRemindersScreen(remindersTestRule) {
            clickReminderList()
        } verify {
            reminderListElementIsShown()
        }
    }

    // Todo Fix or delete this
//    @Test
//    fun onReminderNoteDelete() {
//        launchRemindersScreen(remindersTestRule) {
//            clickDeleteReminderNote()
//        } verify {
//            reminderNoteIsNotShown()
//        }
//    }

    // Todo Fix or delete this
//    @Test
//    fun onLoadingScreenShoesLoading() {
//        launchRemindersScreen(remindersTestRule) {
//            // No action
//        } verify {
//            Thread.sleep(2000)
//            loadingIsShown()
//        }
//    }
}


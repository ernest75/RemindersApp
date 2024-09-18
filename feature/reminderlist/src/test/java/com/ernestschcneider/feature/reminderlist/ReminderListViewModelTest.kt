package com.ernestschcneider.feature.reminderlist

import androidx.lifecycle.SavedStateHandle
import com.ernestschcneider.EMPTY_REMINDER_ID
import com.ernestschcneider.REMINDER_LIST_ID
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.core.dispatchers.CoroutineTestExtension
import com.ernestschneider.testutils.InMemoryLocalRepo
import com.ernestschneider.testutils.ReminderBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify

@ExtendWith(CoroutineTestExtension::class)
class ReminderListViewModelTest {
    private val localRepo = InMemoryLocalRepo()
    private val backgroundDispatcher = Dispatchers.Unconfined
    private val savedStateHandle = getSavedStateHandle()

    private val viewModel = ReminderListViewModel(
        localRepo = localRepo,
        backgroundDispatcher = backgroundDispatcher,
        savedStateHandle = savedStateHandle
    )

    private val reminderNote1 = ReminderBuilder.aReminder()
        .withId("1")
        .withReminderTitle("Title1")
        .withReminderContent("Content1")
        .withReminderType(ReminderType.Note)
        .build()

    private val reminderList1 = ReminderBuilder.aReminder()
        .withId("2")
        .withReminderTitle("Title2")
        .withReminderType(ReminderType.List)
        .withReminderList(arrayListOf("Element1", "Element2"))
        .build()

    @Test
    fun onReminderListTitleUpdate() {
        val reminderListTitle = "reminderListTitle"

        viewModel.onReminderListTitleUpdate(reminderListTitle)

        assertEquals(reminderListTitle, viewModel.screenState.value.reminderListTitle)
        assertTrue(viewModel.screenState.value.showSaveButton)
    }

    @Test
    fun onAddFirstReminderListClicked() {
        viewModel.onAddFirstReminderListClicked()

        assertTrue(viewModel.screenState.value.showCreateReminderDialog)
        assertTrue(viewModel.screenState.value.isFirstReminder)
        assertTrue(viewModel.screenState.value.requestFocus)
        assertEquals(ReminderItem(), viewModel.screenState.value.reminderToEdit)
    }

    @Test
    fun onDismissCreateDialogClicked() {
        // to force showing dialog on screen
        viewModel.onAddFirstReminderListClicked()

        viewModel.onDismissCreateDialogClicked()

        assertFalse(viewModel.screenState.value.showCreateReminderDialog)
    }
    
    @Test
    fun onFirstReminderListItemAdded() {
        val reminderText = "reminderText"
        val reminderText2 = "reminderText2"
        val reminderList = listOf(reminderText2, reminderText)
        viewModel.onFirstReminderListItemAdded(reminderText)

        viewModel.onFirstReminderListItemAdded(reminderText2)

        assertEquals(reminderList, viewModel.screenState.value.remindersList)
        assertTrue(viewModel.screenState.value.scrollListToLast)
    }

    @Test
    fun onAddLastReminderClicked() {
        viewModel.onAddLastReminderListClicked()

        assertTrue(viewModel.screenState.value.showCreateReminderDialog)
        assertFalse(viewModel.screenState.value.isFirstReminder)
        assertTrue(viewModel.screenState.value.requestFocus)
        assertEquals(ReminderItem(), viewModel.screenState.value.reminderToEdit)
    }

    @Test
    fun onLastReminderListItemAdded() {
        val reminderText = "reminderText"
        val reminderText2 = "reminderText2"
        val reminderList = listOf(reminderText, reminderText2)
        viewModel.onLastReminderListItemAdded(reminderText)

        viewModel.onLastReminderListItemAdded(reminderText2)

        assertEquals(reminderList, viewModel.screenState.value.remindersList)
        assertTrue(viewModel.screenState.value.scrollListToLast)
    }

    @Test
    fun onSaveReminderListNotEmptyTitle() {
        val reminderListTitle = "title"
        val firstReminder = "1"
        val lastReminder = "2"
        val reminderList = listOf(firstReminder, lastReminder)
        val backNavigation = true
        viewModel.onFirstReminderListItemAdded(firstReminder)
        viewModel.onLastReminderListItemAdded(lastReminder)
        viewModel.onReminderListTitleUpdate(reminderListTitle)

        viewModel.onSaveListReminderClicked()

        assertEquals(reminderListTitle, viewModel.screenState.value.reminderListTitle)
        assertEquals(reminderList, viewModel.screenState.value.remindersList)
        assertTrue(backNavigation)
    }

    @Test
    fun onSaveReminderListEmptyTitle() {
        val firstReminder = "1"
        val lastReminder = "2"
        viewModel.onFirstReminderListItemAdded(firstReminder)
        viewModel.onLastReminderListItemAdded(lastReminder)

        viewModel.onSaveListReminderClicked()

        assertTrue(viewModel.screenState.value.showEmptyTitleDialog)
    }

    @Test
    fun onDismissEmptyTitleDialogClicked() {
        viewModel.onSaveListReminderClicked()

        viewModel.onDismissEmptyTitleDialogClicked()

        assertFalse(viewModel.screenState.value.showEmptyTitleDialog)
    }

    @Test
    fun onLoadReminderNotEmptyId() = runTest {
        val reminderId = "2"
        val viewModel = ReminderListViewModel(
            savedStateHandle = getSavedStateHandle(reminderListId = reminderId),
            localRepo = localRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        localRepo.saveReminders(listOf(reminderNote1, reminderList1))
        val reminder = localRepo.getReminder(reminderId)

        viewModel.loadReminderList()

        assertEquals(reminder.reminderTitle, viewModel.screenState.value.reminderListTitle)
        assertEquals(reminder.remindersList, viewModel.screenState.value.remindersList)
        assertEquals(reminder.reminderType, ReminderType.List)
        assertEquals(false, viewModel.screenState.value.showSaveButton)
    }

    @Test
    fun onLoadEmptyIdListReminder() {
        val requestFocus = true

        viewModel.loadReminderList()

        assertEquals(requestFocus,viewModel.screenState.value.requestFocus )
    }

    @Test
    fun onSaveExistingReminderList() = runTest{
        val reminderListTitle = "noteTitle"
        val backNavigation = true
        val firstReminder = "1"
        val lastReminder = "2"
        val reminderList = arrayListOf(firstReminder, lastReminder)
        val spiedLocalRepo = spy(localRepo)
        localRepo.saveReminders(listOf(reminderNote1, reminderList1))
        // TODO improve this??
        val viewModel = ReminderListViewModel(
            savedStateHandle = getSavedStateHandle(REMINDER_LIST_ID),
            localRepo = spiedLocalRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        val reminder = ReminderBuilder
            .aReminder()
            .withId(REMINDER_LIST_ID)
            .withReminderTitle(reminderListTitle)
            .withReminderList(reminderList)
            .withReminderType(ReminderType.List)
            .build()
        viewModel.onFirstReminderListItemAdded(firstReminder)
        viewModel.onLastReminderListItemAdded(lastReminder)
        viewModel.onReminderListTitleUpdate(reminderListTitle)

        viewModel.onSaveListReminderClicked()

        verify(spiedLocalRepo).updateReminder(reminder)
        assertEquals(backNavigation, viewModel.screenState.value.backNavigation)
    }

    @Test
    fun onReminderDeleteClicked() {
        val viewModel = ReminderListViewModel(
            savedStateHandle = getSavedStateHandle(REMINDER_LIST_ID),
            localRepo = localRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        val element1 = "element1"
        val element2 = "element2"
        val remindersListDeleted = arrayListOf(element2)
        viewModel.onFirstReminderListItemAdded(element1)
        viewModel.onFirstReminderListItemAdded(element2)
        localRepo.saveReminders(listOf(reminderNote1, reminderList1))

        viewModel.onDeleteReminderItem(element1)

        assertEquals(remindersListDeleted, viewModel.screenState.value.remindersList)
        assertTrue(viewModel.screenState.value.showSaveButton)
        assertTrue(viewModel.screenState.value.scrollListToLast)
    }

    @Test
    fun onReminderEditClicked() {
        val element1 = "element1"
        val element2 = "element2"
        viewModel.onFirstReminderListItemAdded(element1)
        viewModel.onFirstReminderListItemAdded(element2)
        val elementToEdit = ReminderItem(
            pos = 0,
            text = element1
        )

        viewModel.onReminderEditClicked(elementToEdit)

        assertTrue(viewModel.screenState.value.showCreateReminderDialog)
        assertEquals(elementToEdit, viewModel.screenState.value.reminderToEdit)
    }
    
    @Test
    fun onReminderEdited() {
        val element1 = "element1"
        val element2 = "element2"
        viewModel.onFirstReminderListItemAdded(element1)
        viewModel.onLastReminderListItemAdded(element2)
        val element1Modified = "elementModified1"
        val reminderList = listOf(element1Modified, element2)
        val reminderItem = ReminderItem(0, element1Modified)

        viewModel.onReminderEdited(reminderItem)

        assertEquals(reminderList, viewModel.screenState.value.remindersList)
        assertTrue(viewModel.screenState.value.showSaveButton)
    }

    @Test
    fun onMoveListItemSamePosition() {
        val element1 = "element1"
        val element2 = "element2"
        viewModel.onFirstReminderListItemAdded(element1)
        viewModel.onLastReminderListItemAdded(element2)
        val list = viewModel.screenState.value.remindersList
        val samePosition = 2

        viewModel.onMoveListItem(samePosition, samePosition)

        assertEquals(list, viewModel.screenState.value.remindersList)
    }

    @Test
    fun onMoveListItemToFirstNotDraggableElement() {
        val element1 = "element1"
        val element2 = "element2"
        viewModel.onFirstReminderListItemAdded(element1)
        viewModel.onLastReminderListItemAdded(element2)
        val list = viewModel.screenState.value.remindersList
        val firstNotDraggableElement = 0
        val irrelevantFromPosition = 1

        viewModel.onMoveListItem(irrelevantFromPosition, firstNotDraggableElement)

        assertEquals(list, viewModel.screenState.value.remindersList)
    }

    @Test
    fun onMoveListItemToLastNotDraggableElement() {
        val element1 = "element1"
        val element2 = "element2"
        viewModel.onFirstReminderListItemAdded(element1)
        viewModel.onLastReminderListItemAdded(element2)
        val list = viewModel.screenState.value.remindersList
        val lastNotDraggableElement = list.size
        val irrelevantFromPosition = 1

        viewModel.onMoveListItem(irrelevantFromPosition, lastNotDraggableElement)

        assertEquals(list, viewModel.screenState.value.remindersList)
    }

    @Test
    fun onMoveListItemFromFirstNotDraggableElement() {
        val element1 = "element1"
        val element2 = "element2"
        viewModel.onFirstReminderListItemAdded(element1)
        viewModel.onLastReminderListItemAdded(element2)
        val list = viewModel.screenState.value.remindersList
        val firstNotDraggableElement = 0
        val irrelevantFromPosition = 1

        viewModel.onMoveListItem(firstNotDraggableElement, irrelevantFromPosition)

        assertEquals(list, viewModel.screenState.value.remindersList)
    }

    @Test
    fun onMoveListItemFromLastNotDraggableElement() {
        val element1 = "element1"
        val element2 = "element2"
        viewModel.onFirstReminderListItemAdded(element1)
        viewModel.onLastReminderListItemAdded(element2)
        val list = viewModel.screenState.value.remindersList
        val lastNotDraggableElement = list.size
        val irrelevantFromPosition = 1

        viewModel.onMoveListItem(lastNotDraggableElement, irrelevantFromPosition)

        assertEquals(list, viewModel.screenState.value.remindersList)
    }

    private fun getSavedStateHandle(reminderListId: String = EMPTY_REMINDER_ID): SavedStateHandle {
        return SavedStateHandle().apply {
            set(REMINDER_LIST_ID_ARG, reminderListId)
        }
    }
}

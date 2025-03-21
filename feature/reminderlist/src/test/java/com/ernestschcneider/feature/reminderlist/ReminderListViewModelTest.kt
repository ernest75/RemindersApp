package com.ernestschcneider.feature.reminderlist

import androidx.lifecycle.SavedStateHandle
import com.ernestschcneider.EMPTY_REMINDER_ID
import com.ernestschcneider.REMINDER_LIST_ID
import com.ernestschcneider.models.ReminderListItem
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

    private val reminderNote1 = ReminderBuilder.aReminder().withId("1").withReminderTitle("Title1")
        .withReminderContent("Content1").withReminderType(ReminderType.Note).build()

    private val reminderList1 = ReminderBuilder.aReminder().withId("2").withReminderTitle("Title2")
        .withReminderType(ReminderType.List).withReminderList(
            arrayListOf(
                ReminderListItem(position = 0, text = "Element1"),
                ReminderListItem(position = 1, text = "Element2")
            )
        ).build()

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
        assertEquals(ReminderListItem(), viewModel.screenState.value.reminderToEdit)
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
        val firstReminder = ReminderListItem(position = 0, reminderText2)
        val lastReminder = ReminderListItem(position = 1, reminderText)
        val reminderList = listOf(firstReminder, lastReminder)
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
        assertEquals(ReminderListItem(), viewModel.screenState.value.reminderToEdit)
    }

    @Test
    fun onLastReminderListItemAdded() {
        val reminderText = "reminderText"
        val reminderText2 = "reminderText2"
        val firstReminderList = ReminderListItem(text = reminderText, position = 0)
        val lastReminderList = ReminderListItem(text = reminderText2, position = 1)
        val reminderList = listOf(firstReminderList, lastReminderList)
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
        val reminderList = listOf(
            ReminderListItem(text = firstReminder, position = 0),
            ReminderListItem(text = lastReminder, position = 1)
        )
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

        assertEquals(requestFocus, viewModel.screenState.value.requestFocus)
    }

    @Test
    fun onSaveExistingReminderList() = runTest {
        val reminderListTitle = "noteTitle"
        val backNavigation = true
        val firstReminder = ReminderListItem(text = "1", position = 0)
        val lastReminder = ReminderListItem(text = "2", position = 1)
        val reminderList = arrayListOf(firstReminder, lastReminder)
        val spiedLocalRepo = spy(localRepo)
        localRepo.saveReminders(listOf(reminderNote1, reminderList1))
        val viewModel = ReminderListViewModel(
            savedStateHandle = getSavedStateHandle(REMINDER_LIST_ID),
            localRepo = spiedLocalRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        val reminder = ReminderBuilder.aReminder().withId(REMINDER_LIST_ID)
            .withReminderTitle(reminderListTitle).withReminderList(reminderList)
            .withReminderType(ReminderType.List).build()
        viewModel.onFirstReminderListItemAdded(firstReminder.text)
        viewModel.onLastReminderListItemAdded(lastReminder.text)
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
        val remindersListDeleted = arrayListOf(ReminderListItem(text = element2, position = 0))
        viewModel.onFirstReminderListItemAdded(element1)
        viewModel.onFirstReminderListItemAdded(element2)
        val reminderToDelete = ReminderListItem(
            text = element1, position = 1
        )

        viewModel.onDeleteReminderItem(reminderToDelete)

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
        val elementToEdit = ReminderListItem(
            position = 0, text = element1
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
        val reminderNotEdited = ReminderListItem(text = element2, position = 1)
        val element1Modified = "elementModified1"
        val reminderItemEdited = ReminderListItem(text = element1Modified, position = 0)
        val reminderList = listOf(reminderItemEdited, reminderNotEdited)

        viewModel.onReminderEdited(reminderItemEdited)

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
        val lastNotDraggableElement = list.size + 1
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
        val lastNotDraggableElement = list.size + 1
        val irrelevantFromPosition = 1

        viewModel.onMoveListItem(lastNotDraggableElement, irrelevantFromPosition)

        assertEquals(list, viewModel.screenState.value.remindersList)
    }

    @Test
    fun onUnCrossedElementCrossed() = runTest {
        val reminderId = "2"
        val viewModel = ReminderListViewModel(
            savedStateHandle = getSavedStateHandle(reminderListId = reminderId),
            localRepo = localRepo,
            backgroundDispatcher = backgroundDispatcher
        )
        localRepo.saveReminders(listOf(reminderList1))
        val reminder = localRepo.getReminder(reminderId)
        val reminderItemToCross = reminder.remindersList.first()
        viewModel.loadReminderList()
        val reminderListItemCrossed = reminderItemToCross.copy(isCrossed = true)

        viewModel.onCrossReminder(reminderItemToCross)

        assertEquals(reminderListItemCrossed, viewModel.screenState.value.remindersList.first())
    }

    @Test
    fun onDragFinishedWithPositionChanges() {
        val reminderText1 = "1"
        val reminderText2 = "2"
        viewModel.onFirstReminderListItemAdded(reminderText2)
        viewModel.onFirstReminderListItemAdded(reminderText1)
        val reminderListItemFinal1 = ReminderListItem(position = 0, text = reminderText1)
        val reminderListItemFinal2 = ReminderListItem(position = 1, text = reminderText2)
        val reminderList = listOf(
            reminderListItemFinal1,
            reminderListItemFinal2
        )
        viewModel.onMoveListItem(0,1)

        viewModel.onDragFinished()

        assertEquals(reminderList, viewModel.screenState.value.remindersList)
    }

    @Test
    fun onDragFinishedWithNoPositionChanges() {
        val reminderText1 = "1"
        val reminderText2 = "2"
        viewModel.onFirstReminderListItemAdded(reminderText2)
        viewModel.onFirstReminderListItemAdded(reminderText1)
        val reminderListItemFinal1 = ReminderListItem(position = 0, text = reminderText1)
        val reminderListItemFinal2 = ReminderListItem(position = 1, text = reminderText2)
        val reminderList = listOf(
            reminderListItemFinal1,
            reminderListItemFinal2
        )

        viewModel.onDragFinished()

        assertEquals(reminderList, viewModel.screenState.value.remindersList)
    }

    private fun getSavedStateHandle(reminderListId: String = EMPTY_REMINDER_ID): SavedStateHandle {
        return SavedStateHandle().apply {
            set(REMINDER_LIST_ID_ARG, reminderListId)
        }
    }
}

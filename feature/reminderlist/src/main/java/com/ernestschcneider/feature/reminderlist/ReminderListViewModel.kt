package com.ernestschcneider.feature.reminderlist

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.remindersapp.core.commons.EMPTY_REMINDER_ID
import com.ernestschcneider.remindersapp.data.usecases.CountRemindersUseCase
import com.ernestschcneider.remindersapp.data.usecases.GetReminderUseCase
import com.ernestschcneider.remindersapp.data.usecases.SaveReminderUseCase
import com.ernestschcneider.remindersapp.data.usecases.UpdateReminderUseCase
import com.ernestschcneider.remindersapp.models.Reminder
import com.ernestschcneider.remindersapp.models.ReminderListItem
import com.ernestschcneider.remindersapp.models.ReminderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val FIRST_NOT_DRAGGABLE_ELEMENT = 0
private const val TO_OBTAIN_TOTAL_DRAGGABLE_ELEMENTS = 1

@HiltViewModel
class ReminderListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val countRemindersUseCase: CountRemindersUseCase,
    private val saveReminderUseCase: SaveReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase,
    private val getReminderUseCase: GetReminderUseCase,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _screenState = MutableStateFlow(ReminderListState())
    val screenState: StateFlow<ReminderListState> = _screenState.asStateFlow()
    private val reminderListArgs = ReminderListArgs(savedStateHandle)

    fun onReminderListTitleUpdate(reminderListTitle: TextFieldValue) {
        _screenState.update {
            it.copy(
                reminderListTitle = reminderListTitle,
                showSaveButton = true
            )
        }
    }

    fun onAddFirstReminderListClicked() {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = true,
                isFirstReminder = true,
                reminderToEdit = ReminderListItem()
            )
        }
    }

    fun onFirstReminderListItemAdded(reminderText: String) {
        val firstIndex = FIRST_NOT_DRAGGABLE_ELEMENT
        val reminderItem = ReminderListItem(
            text = reminderText,
            position = firstIndex
        )
        val list = _screenState.value.remindersList.toMutableList().apply {
            add(firstIndex, reminderItem)
        }
        updateReminderListPositions(list)
        _screenState.update {
            it.copy(
                remindersList = list,
                showSaveButton = true,
                scrollListToLast = true
            )
        }
    }

    private fun updateReminderListPositions(reminderList: List<ReminderListItem>) {
        reminderList.forEachIndexed { index, reminderListItem ->
            reminderListItem.position = index
        }
    }

    fun onDismissCreateDialogClicked() {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = false
            )
        }
    }

    fun onAddLastReminderListClicked() {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = true,
                isFirstReminder = false,
                reminderToEdit = ReminderListItem()
            )
        }
    }

    fun onLastReminderListItemAdded(reminderText: String) {
        val reminderItem = ReminderListItem(
            text = reminderText,
        )
        val list = _screenState.value.remindersList.toMutableList().apply {
            add(reminderItem)
        }
        updateReminderListPositions(list)
        _screenState.update {
            it.copy(
                remindersList = list,
                showSaveButton = true,
                scrollListToLast = true
            )
        }
    }

    fun onSaveListReminderClicked() {
        if (_screenState.value.reminderListTitle.text.isNotEmpty()) {
            val remindersArray = arrayListOf<ReminderListItem>().apply {
                addAll(_screenState.value.remindersList)
            }
            val reminderId = reminderListArgs.reminderListId
            viewModelScope.launch {
                withContext(backgroundDispatcher) {
                    if (reminderId == EMPTY_REMINDER_ID) {
                        val position = countRemindersUseCase()
                        val reminder = Reminder(
                            reminderTitle = _screenState.value.reminderListTitle.text,
                            remindersList = remindersArray,
                            reminderType = ReminderType.List,
                            reminderPosition = position
                        )
                        saveReminderUseCase(reminder)
                    } else {
                        val position = getReminderUseCase(reminderId).reminderPosition
                        val reminder = Reminder(
                            reminderId = reminderId,
                            reminderTitle = _screenState.value.reminderListTitle.text,
                            remindersList = remindersArray,
                            reminderType = ReminderType.List,
                            reminderPosition = position
                        )
                        updateReminderUseCase(reminder)
                    }
                    _screenState.update { it.copy(backNavigation = true) }
                }
            }
        } else {
            _screenState.update { it.copy(showEmptyTitleDialog = true) }

        }
    }

    fun onDismissEmptyTitleDialogClicked() {
        _screenState.update {
            it.copy(
                showEmptyTitleDialog = false
            )
        }
    }

    fun loadReminderList(showSaveButton: Boolean = false) {
        val reminderId = reminderListArgs.reminderListId
        if (reminderId == EMPTY_REMINDER_ID) {
            _screenState.update {
                it.copy(requestFocus = true)
            }
        } else {
            viewModelScope.launch {
                withContext(backgroundDispatcher) {
                    val reminder = getReminderUseCase(reminderId)
                    _screenState.update {
                        it.copy(
                            reminderListTitle = TextFieldValue(reminder.reminderTitle),
                            remindersList = reminder.remindersList,
                            showSaveButton = showSaveButton
                        )
                    }
                }
            }
        }
    }

    fun onDeleteReminderItem(item: ReminderListItem) {
        val remindersArray = arrayListOf<ReminderListItem>().apply {
            addAll(_screenState.value.remindersList)
        }
        remindersArray.remove(item)
        remindersArray.forEachIndexed { index, reminderListItem ->
            reminderListItem.position = index
        }
        _screenState.update {
            it.copy(
                remindersList = remindersArray,
                showSaveButton = true
            )
        }
    }

    fun onReminderEditClicked(item: ReminderListItem) {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = true,
                reminderToEdit = item
            )
        }
    }

    fun onReminderEdited(reminderItem: ReminderListItem) {
        val index = reminderItem.position
        val list = _screenState.value.remindersList.toMutableList()
        list[index] = reminderItem

        _screenState.update {
            it.copy(
                remindersList = list,
                showSaveButton = true
            )
        }
    }

    fun onMoveListItem(from: Int, to: Int) {
        val list = _screenState.value.remindersList.toMutableList()
        if (from == to || to == FIRST_NOT_DRAGGABLE_ELEMENT || to > list.size || from == FIRST_NOT_DRAGGABLE_ELEMENT || from > list.size) return
        list.apply {
            val element = this.removeAt(from - TO_OBTAIN_TOTAL_DRAGGABLE_ELEMENTS)
            this.add(to - TO_OBTAIN_TOTAL_DRAGGABLE_ELEMENTS, element)
        }
        _screenState.update {
            it.copy(
                remindersList = list,
                showSaveButton = true
            )
        }
    }

    fun onCrossReminder(reminderListItem: ReminderListItem) {
        val reminderCrossChanged = reminderListItem.copy(isCrossed = !reminderListItem.isCrossed)
        val list = screenState.value.remindersList.toMutableList()
        list[reminderListItem.position] = reminderCrossChanged
        _screenState.update {
            it.copy(
                remindersList = list,
                showSaveButton = true
            )
        }
    }

    fun onDragFinished() {
        updateReminderListPositions(screenState.value.remindersList)
    }
}

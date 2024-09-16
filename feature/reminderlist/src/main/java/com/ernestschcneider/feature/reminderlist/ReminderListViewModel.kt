package com.ernestschcneider.feature.reminderlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.EMPTY_REMINDER_ID
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.local.StorageRepo
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
    private val localRepo: StorageRepo,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _screenState = MutableStateFlow(ReminderListState())
    val screenState: StateFlow<ReminderListState> = _screenState.asStateFlow()
    private val reminderListArgs = ReminderListArgs(savedStateHandle)

    fun onReminderListTitleUpdate(reminderListTitle: String) {
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
                requestFocus = true,
                isFirstReminder = true,
                reminderToEdit = ReminderItem()
            )
        }
    }

    fun onFirstReminderListItemAdded(reminderText: String) {
        val firstIndex = FIRST_NOT_DRAGGABLE_ELEMENT
        _screenState.value.remindersList.apply {
            add(firstIndex, reminderText)
        }
        _screenState.update {
            it.copy(
                remindersList = _screenState.value.remindersList,
                showSaveButton = true,
                scrollListToLast = true
            )
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
                requestFocus = true,
                isFirstReminder = false,
                reminderToEdit = ReminderItem()
            )
        }
    }

    fun onLastReminderListItemAdded(reminderText: String) {
        _screenState.value.remindersList.apply {
            add(reminderText)
        }
        _screenState.update {
            it.copy(
                remindersList = _screenState.value.remindersList,
                showSaveButton = true,
                scrollListToLast = true
            )
        }
    }

    fun onSaveListReminderClicked() {
        if (_screenState.value.reminderListTitle.isNotEmpty()) {
            val remindersArray = arrayListOf<String>().apply {
                addAll(_screenState.value.remindersList)
            }
            val reminderId = reminderListArgs.reminderListId
            viewModelScope.launch {
                withContext(backgroundDispatcher) {
                    if (reminderId == EMPTY_REMINDER_ID) {
                        val position = localRepo.getAllReminders().size
                        val reminder = Reminder(
                            reminderTitle = _screenState.value.reminderListTitle,
                            remindersList = remindersArray,
                            reminderType = ReminderType.List,
                            reminderPosition = position
                        )
                        localRepo.saveReminder(reminder)
                    } else {
                        val position = localRepo.getReminder(reminderId).reminderPosition
                        val reminder = Reminder(
                            reminderId = reminderId,
                            reminderTitle = _screenState.value.reminderListTitle,
                            remindersList = remindersArray,
                            reminderType = ReminderType.List,
                            reminderPosition = position
                        )
                        localRepo.updateReminder(reminder)
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

    fun loadReminderList() {
        val reminderId = reminderListArgs.reminderListId
        if (reminderId == EMPTY_REMINDER_ID) {
            _screenState.update {
                it.copy(requestFocus = true)
            }
        } else {
            viewModelScope.launch {
                withContext(backgroundDispatcher) {
                    val reminder = localRepo.getReminder(reminderId)
                    _screenState.update {
                        it.copy(
                            reminderListTitle = reminder.reminderTitle,
                            remindersList = reminder.remindersList,
                            showSaveButton = false
                        )
                    }
                }
            }
        }
    }

    fun onDeleteReminderItem(item: String) {
        _screenState.value.remindersList.apply { remove(item) }
        _screenState.update {
            it.copy(
                remindersList = _screenState.value.remindersList,
                showSaveButton = true,
                requestFocus = false
            )
        }
    }

    fun onReminderEditClicked(item: ReminderItem) {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = true,
                reminderToEdit = item
            )
        }
    }

    fun onReminderEdited(reminderItem: ReminderItem) {
        val index = reminderItem.pos
        val list = _screenState.value.remindersList
        list.removeAt(index)
        list.add(index, reminderItem.text)

        _screenState.update {
            it.copy(remindersList = list,
                showSaveButton = true
            )
        }
    }

    fun onMoveListItem(from: Int, to: Int) {
        val list = _screenState.value.remindersList.toMutableList()
        if (from == to || to == FIRST_NOT_DRAGGABLE_ELEMENT || to >= list.size || from == FIRST_NOT_DRAGGABLE_ELEMENT || from >= list.size) return
        list.apply {
            val element = this.removeAt(from - TO_OBTAIN_TOTAL_DRAGGABLE_ELEMENTS)
            this.add(to - TO_OBTAIN_TOTAL_DRAGGABLE_ELEMENTS, element)
        }
        _screenState.update {
            it.copy(
                remindersList = list
            )
        }
    }
}

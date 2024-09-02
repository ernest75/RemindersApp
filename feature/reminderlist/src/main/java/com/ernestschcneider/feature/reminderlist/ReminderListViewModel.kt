package com.ernestschcneider.feature.reminderlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ernestschcneider.remindersapp.local.StorageRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReminderListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localRepo: StorageRepo,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _screenState = MutableStateFlow(ReminderListState())
    val screenState: StateFlow<ReminderListState> = _screenState.asStateFlow()
    private val reminderNoteArgs = ReminderListArgs(savedStateHandle)

    fun onReminderListTitleUpdate(reminderListTitle: String) {
        _screenState.update {
            it.copy(
                reminderListTitle = reminderListTitle,
                showSaveButton = true
            )
        }
    }

    fun onFirstReminderListClicked() {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = true,
                requestFocus = true
            )
        }
    }

    fun onDismissDialogClicked() {
        _screenState.update {
            it.copy(
                showCreateReminderDialog = false
            )
        }
    }
}

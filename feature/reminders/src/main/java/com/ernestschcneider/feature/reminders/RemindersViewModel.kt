package com.ernestschcneider.feature.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.remindersapp.local.StorageRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel  @Inject constructor(
    private val localRepo: StorageRepo,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _screenState = MutableStateFlow(RemindersScreenState())
    val screenState: StateFlow<RemindersScreenState> = _screenState.asStateFlow()

    fun loadReminders() {
        viewModelScope.launch {
            withContext(backgroundDispatcher) {
                showLoading()
                val reminders = localRepo.getAllReminders().sortedBy { it.reminderPosition }
                _screenState.update { it.copy(reminders = reminders, showLoading = false) }
                savePositions()
            }
        }

    }

    fun removeItem(item: Reminder) {
        viewModelScope.launch {
            withContext(backgroundDispatcher) {
                localRepo.deleteReminder(item)
                loadReminders()
            }
        }
    }

    fun onAddButtonClicked() {
        _screenState.update { it.copy(showCreationDialog = true) }
    }

    fun onDismissDialog() {
        _screenState.update { it.copy(showCreationDialog = false) }
    }

    fun onMoveReminder(from: Int, to: Int) {
        if (from == to) return
        val list = _screenState.value.reminders.toMutableList()
        list.apply {
            val element = this.removeAt(from)
            this.add(to, element)
        }
        _screenState.update { it.copy(
            reminders = list
        ) }
    }

    fun onReminderMoved() {
        savePositions()
    }

    private fun savePositions() {
        viewModelScope.launch {
            withContext(backgroundDispatcher) {
                val list = _screenState.value.reminders
                list.forEachIndexed { index, reminder ->
                    localRepo.updateReminderPosition(index, reminder.reminderId)
                }
            }
        }
    }

    fun showLoading() {
        _screenState.update { it.copy(showLoading = true) }
    }
}

package com.ernestschcneider.feature.remiderdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReminderDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _screenState = MutableStateFlow(ReminderDetailsScreenState())
    private val reminderDetailsArgs = ReminderDetailsArgs(savedStateHandle)

    val screenState = _screenState.asStateFlow()

    fun loadRemindersDetails() {
        val reminderId = reminderDetailsArgs.reminderId
        _screenState.update { it.copy(title = reminderId) }
    }
}
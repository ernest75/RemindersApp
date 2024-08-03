package com.ernestschcneider.feature.remindercreation.notecreation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteCreationViewModel: ViewModel() {
    private val _screenState = MutableStateFlow(NoteCreationState())
    val screenState: StateFlow<NoteCreationState> = _screenState.asStateFlow()
}
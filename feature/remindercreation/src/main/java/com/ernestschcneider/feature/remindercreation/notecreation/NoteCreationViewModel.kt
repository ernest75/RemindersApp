package com.ernestschcneider.feature.remindercreation.notecreation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NoteCreationViewModel: ViewModel() {

    private val _screenState = MutableStateFlow(NoteCreationState())
    val screenState: StateFlow<NoteCreationState> = _screenState.asStateFlow()

    fun onNoteUpdate(note: String) {
        _screenState.update { it.copy(noteContent = note) }
    }

}
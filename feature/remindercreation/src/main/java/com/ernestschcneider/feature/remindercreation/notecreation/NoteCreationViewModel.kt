package com.ernestschcneider.feature.remindercreation.notecreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.remindersapp.local.LocalRepo
import com.ernestschcneider.remindersapp.local.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteCreationViewModel @Inject constructor(
    private val localRepo: LocalRepo
): ViewModel() {

    private val _screenState = MutableStateFlow(NoteCreationState())
    val screenState: StateFlow<NoteCreationState> = _screenState.asStateFlow()
    var note = Note(
        id = "2",
        noteTitle = "noteTitle",
        noteContent = "noteContent"
    )

    fun onNoteUpdate(note: String) {
        _screenState.update { it.copy(noteContent = note) }
    }

    fun onSavedNoteClicked() {
        viewModelScope.launch {
            localRepo.saveNote(note)
        }
    }
}

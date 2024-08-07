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
) : ViewModel() {

    private val _screenState = MutableStateFlow(NoteCreationState())
    val screenState: StateFlow<NoteCreationState> = _screenState.asStateFlow()

    private var noteTitle: String? = null
    private var noteContent: String? = null

    fun onNoteContentUpdate(noteContent: String) {
        this.noteContent = noteContent
        _screenState.update { it.copy(noteContent = noteContent) }
    }

    fun onNoteTitleUpdate(noteTitle: String) {
        this.noteTitle = noteTitle
    }

    fun onSavedNoteClicked() {
        // TODO add dialog informing note is empty if titlte and content are?
        if (!noteContent.isNullOrEmpty() || !noteTitle.isNullOrEmpty().not()) {
            val note = Note(
                noteTitle = noteTitle.orEmpty(),
                noteContent = noteContent.orEmpty()
            )
            viewModelScope.launch {
                localRepo.saveNote(note)
            }
        } else {
            //TODO add dialog??
            println()
        }

    }
}

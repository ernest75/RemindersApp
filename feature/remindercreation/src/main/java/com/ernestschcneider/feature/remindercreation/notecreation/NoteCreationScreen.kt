package com.ernestschcneider.feature.remindercreation.notecreation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.remindersapp.core.view.composables.RemindersTopAppBar
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun NoteCreationScreen(
    noteCreationViewModel: NoteCreationViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by noteCreationViewModel.screenState.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    NoteCreationScreenContent(
        onNavigateUp = onNavigateUp,
        state = state,
        onNoteUpdate = noteCreationViewModel::onNoteUpdate,
        focusRequester = focusRequester

    )
}

@Composable
fun NoteCreationScreenContent(
    state: NoteCreationState,
    onNavigateUp: () -> Unit,
    onNoteUpdate: (String) -> Unit,
    focusRequester: FocusRequester
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
    ){
        RemindersTopAppBar(
            onNavigateUp = onNavigateUp,
            onTitleUpdate = {},
            focusRequester = focusRequester
        )

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = state.noteContent,
            onValueChange = onNoteUpdate,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppTheme.colorScheme.secondaryContainer,
                disabledContainerColor = AppTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = AppTheme.colorScheme.secondaryContainer
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
    }

}

@PreviewLightDark
@Composable
private fun NoteCreationScreenPreview() {
    AppTheme {
        NoteCreationScreenContent(
            onNavigateUp = {},
            onNoteUpdate = {},
            state = NoteCreationState(),
            focusRequester = FocusRequester()
        )
    }
}

package com.ernestschcneider.feature.remindercreation.notecreation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.remindersapp.core.view.R.string
import com.ernestschcneider.remindersapp.core.view.composables.RemindersTopAppBar
import com.ernestschcneider.remindersapp.core.view.composables.SecondaryButton
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
            .padding(top = 48.dp)
            .fillMaxSize()
            .background(AppTheme.colorScheme.secondaryContainer)
    ) {
        RemindersTopAppBar(
            onNavigateUp = onNavigateUp,
            onTitleUpdate = {},
            focusRequester = focusRequester
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.noteContent,
            onValueChange = onNoteUpdate,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppTheme.colorScheme.secondaryContainer,
                disabledContainerColor = AppTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = AppTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            placeholder = { Text(stringResource(id = string.type_note_text), color = AppTheme.colorScheme.secondary) }
        )
        HorizontalDivider()
        SecondaryButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top= 4.dp),
            label = stringResource(id = string.save_note),
            onClick = {}
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

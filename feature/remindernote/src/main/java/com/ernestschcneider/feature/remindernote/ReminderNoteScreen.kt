package com.ernestschcneider.feature.remindernote

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
import com.ernestschcneider.remindersapp.core.view.composables.InformativeDialog
import com.ernestschcneider.remindersapp.core.view.composables.PrimaryButton
import com.ernestschcneider.remindersapp.core.view.composables.RemindersTopAppBar
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun ReminderNoteScreen(
    reminderNoteViewModel: ReminderNoteViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by reminderNoteViewModel.screenState.collectAsStateWithLifecycle()

    if (state.backNavigation) {
        onNavigateUp()
    }

    LaunchedEffect(Unit) {
        reminderNoteViewModel.loadNoteReminder()
    }
    ReminderNoteScreenContent(
        onNavigateUp = onNavigateUp,
        state = state,
        onNoteContentUpdate = reminderNoteViewModel::onNoteContentUpdate,
        onNoteSaved = reminderNoteViewModel::onSavedNoteClicked,
        onNoteTitleUpdate = reminderNoteViewModel::onNoteTitleUpdate,
        onDismissEmptyTitleDialog = reminderNoteViewModel::onDismissEmptyTitleDialog

    )
}

@Composable
fun ReminderNoteScreenContent(
    state: ReminderNoteState,
    onNavigateUp: () -> Unit,
    onNoteContentUpdate: (String) -> Unit,
    onNoteTitleUpdate: (String) -> Unit,
    onNoteSaved: () -> Unit,
    onDismissEmptyTitleDialog: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    if (state.requestFocus) {
        focusRequester.requestFocus()
    }

    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .padding(top = 48.dp)
            .fillMaxSize()
            .background(AppTheme.colorScheme.secondaryContainer)
    ) {
        RemindersTopAppBar(
            onNavigateUp = onNavigateUp,
            onTitleUpdate = onNoteTitleUpdate,
            focusRequester = focusRequester,
            value = state.reminderTitle
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.reminderContent,
            onValueChange = onNoteContentUpdate,
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
            placeholder = {
                Text(
                    stringResource(id = string.type_reminder_text),
                    color = AppTheme.colorScheme.secondary
                )
            }
        )
        HorizontalDivider()
        PrimaryButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            label = stringResource(id = string.save_reminder),
            onClick = onNoteSaved,
            isVisible = state.showSaveButton
        )
        if (state.showEmptyTitleDialog) {
            InformativeDialog(
                titleId = string.warning,
                explanationId = string.empty_title_explanation,
                onDismiss = onDismissEmptyTitleDialog
            )
        }
    }

}

@PreviewLightDark
@Composable
private fun NoteCreationScreenPreview() {
    AppTheme {
        ReminderNoteScreenContent(
            onNavigateUp = {},
            onNoteContentUpdate = {},
            state = ReminderNoteState(showSaveButton = true),
            onNoteSaved = {},
            onNoteTitleUpdate = {},
            onDismissEmptyTitleDialog = {}
        )
    }
}

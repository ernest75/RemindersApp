package com.ernestschcneider.feature.remindernote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.remindersapp.core.testtags.*
import com.ernestschcneider.remindersapp.core.view.R
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
        reminderNoteViewModel.loadReminder()
    }
    ReminderNoteScreenContent(
        onNavigateUp = onNavigateUp,
        state = state,
        onReminderNoteContentUpdate = reminderNoteViewModel::onReminderContentUpdate,
        onReminderNoteSaved = reminderNoteViewModel::onSavedReminderClicked,
        onReminderNoteTitleUpdate = reminderNoteViewModel::onReminderTitleUpdate,
        onDismissEmptyTitleDialog = reminderNoteViewModel::onDismissEmptyTitleDialog

    )
}

@Composable
fun ReminderNoteScreenContent(
    state: ReminderNoteState,
    onNavigateUp: () -> Unit,
    onReminderNoteContentUpdate: (TextFieldValue) -> Unit,
    onReminderNoteTitleUpdate: (String) -> Unit,
    onReminderNoteSaved: () -> Unit,
    onDismissEmptyTitleDialog: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    if (state.requestFocus) {
        focusRequester.requestFocus()
    }

    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .padding(top = 48.dp)
            .fillMaxSize()
            .testTag(REMINDER_NOTE),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            PrimaryButton(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .fillMaxWidth()
                    .testTag(REMINDER_NOTE_SAVE_BUTTON),
                label = stringResource(id = string.save_changes),
                onClick = onReminderNoteSaved,
                isVisible = state.showSaveButton
            )
        },
        topBar = {
            RemindersTopAppBar(
                modifier = Modifier.testTag(REMINDER_NOTE_TOP_BAR),
                onNavigateUp = onNavigateUp,
                onTitleUpdate = onReminderNoteTitleUpdate,
                focusRequester = focusRequester,
                value = state.reminderTitle,
                titlePlaceHolderId = string.type_reminder_title
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(AppTheme.colorScheme.secondaryContainer)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(REMINDER_NOTE_TEXT_FIELD),
                value = TextFieldValue(
                    text = state.reminderContent,
                    selection = TextRange(state.reminderContent.length)
                ),
                onValueChange = onReminderNoteContentUpdate,
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
                    onReminderNoteSaved()
                }),
                textStyle = AppTheme.typography.labelLarge,
                placeholder = {
                    Text(
                        text = stringResource(id = string.type_reminder_text),
                        color = AppTheme.colorScheme.secondary
                    )
                }
            )

        }
        if (state.showEmptyTitleDialog){
            InformativeDialog(
                onDismiss = onDismissEmptyTitleDialog,
                titleId = R.string.warning,
                explanationId = R.string.empty_title_explanation)
        }
    }
}

@PreviewLightDark
@Composable
private fun NoteCreationScreenPreview() {
    AppTheme {
        ReminderNoteScreenContent(
            onNavigateUp = {},
            onReminderNoteContentUpdate = {},
            state = ReminderNoteState(showSaveButton = true),
            onReminderNoteSaved = {},
            onReminderNoteTitleUpdate = {},
            onDismissEmptyTitleDialog = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun NoteCreationScreenEmptyDialogPreview() {
    AppTheme {
        ReminderNoteScreenContent(
            onNavigateUp = {},
            onReminderNoteContentUpdate = {},
            state = ReminderNoteState(showEmptyTitleDialog = true, showSaveButton = true),
            onReminderNoteSaved = {},
            onReminderNoteTitleUpdate = {},
            onDismissEmptyTitleDialog = {}
        )
    }
}

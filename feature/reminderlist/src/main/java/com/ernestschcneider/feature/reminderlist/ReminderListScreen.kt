package com.ernestschcneider.feature.reminderlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.feature.reminderlist.views.AddReminder
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.composables.RemindersTopAppBar
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun ReminderListScreen(
    reminderListViewModel: ReminderListViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by reminderListViewModel.screenState.collectAsStateWithLifecycle()

    ReminderListScreenContent(
        onNavigateUp = onNavigateUp,
        state = state
    )

}

@Composable
fun ReminderListScreenContent(
    onNavigateUp: () -> Unit,
    state: ReminderListState
) {
    val focusRequester = remember { FocusRequester() }
    if (state.requestFocus) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
            .background(AppTheme.colorScheme.secondaryContainer)
    ) {
        RemindersTopAppBar(
            onNavigateUp = onNavigateUp,
            onTitleUpdate = {},
            focusRequester = focusRequester,
            value = state.reminderListTitle
        )
        AddReminder(
            onAddReminderClicked = {}
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 24.dp),
            thickness = 1.dp,
            color = AppTheme.colorScheme.scrim
        )

    }
}

@PreviewLightDark
@Composable
private fun NoteCreationScreenPreview() {
    AppTheme {
        ReminderListScreenContent(
            onNavigateUp = {},
            state = ReminderListState()
        )
    }
}


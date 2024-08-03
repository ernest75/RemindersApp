package com.ernestschcneider.feature.remindercreation.notecreation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
internal fun NoteCreationScreen(
    noteCreationViewModel: NoteCreationViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by noteCreationViewModel.screenState.collectAsStateWithLifecycle()
    NoteCreationScreenContent(
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun NoteCreationScreenContent(onNavigateUp: () -> Unit) {
    Text(text = "Note Creation Screen")
}

@PreviewLightDark
@Composable
private fun NoteCreationScreenPreview() {
    AppTheme {
        NoteCreationScreenContent(
            onNavigateUp = {}
        )
    }
}

package com.ernestschcneider.feature.remindercreation.notecreation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    NoteCreationScreenContent(
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun NoteCreationScreenContent(onNavigateUp: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
    ){
        RemindersTopAppBar(
            onNavigateUp = onNavigateUp,
            onTitleUpdate = {}
        )
    }

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

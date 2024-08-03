package com.ernestschcneider.feature.remindercreation.notecreation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val NOTE_CREATION_ROUTE = "noteCreation"

fun NavGraphBuilder.noteCreationScreen(
    onNavigateUp: () -> Unit
){
    composable(NOTE_CREATION_ROUTE) {
        NoteCreationScreen(
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToNoteCreation() {
    navigate(NOTE_CREATION_ROUTE)
}

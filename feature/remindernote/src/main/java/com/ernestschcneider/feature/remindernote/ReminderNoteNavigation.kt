package com.ernestschcneider.feature.remindernote

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REMINDER_ID_ARG = "reminderId"
const val REMINDER_NOTE_DETAILS_ROUTE = "reminderNote/$REMINDER_ID_ARG={$REMINDER_ID_ARG}"

fun NavGraphBuilder.noteCreationScreen(
    onNavigateUp: () -> Unit
){
    composable(REMINDER_NOTE_DETAILS_ROUTE) {
        ReminderNoteScreen(
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToNoteCreation(reminderId: String) {
    navigate("reminderNote/$REMINDER_ID_ARG=$reminderId")
}

internal class ReminderNoteArgs(val reminderId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(requireNotNull(savedStateHandle.get<String>(REMINDER_ID_ARG)))
}

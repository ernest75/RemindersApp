package com.ernestschcneider.feature.reminderlist

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REMINDER_LIST_ID_ARG = "reminderListId"
const val REMINDER_LIST_ROUTE = "reminderListNote/$REMINDER_LIST_ID_ARG={$REMINDER_LIST_ID_ARG}"

fun NavGraphBuilder.reminderListScreen(
    onNavigateUp: () -> Unit
) {
   composable(REMINDER_LIST_ROUTE) {
       ReminderListScreen(
           onNavigateUp = onNavigateUp
       )
   }
}

fun NavController.navigateToReminderList(reminderListId: String) {
    navigate("reminderListNote/$REMINDER_LIST_ID_ARG=$reminderListId")
}

internal class ReminderListArgs(val reminderListId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(requireNotNull(savedStateHandle.get<String>(REMINDER_LIST_ID_ARG)))
}
package com.ernestschcneider.feature.remiderdetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REMINDER_ID_ARG = "postId"
const val REMINDER_DETAILS_ROUTE = "remindersDetails/$REMINDER_ID_ARG={$REMINDER_ID_ARG}"

fun NavGraphBuilder.remindersDetailScreen(
    onNavigateUp: () -> Unit
) {
    composable(REMINDER_DETAILS_ROUTE) {
        ReminderDetailScreen(
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToReminderDetailsScreen(
    reminderId: String
) {
    navigate("remindersDetails/$REMINDER_ID_ARG=$reminderId")
}

internal class ReminderDetailsArgs(val reminderId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(requireNotNull(savedStateHandle.get<String>(REMINDER_ID_ARG)))
}


package com.ernestschcneider.remindersapp

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ernestschcneider.REMINDERS_ROUTE
import com.ernestschcneider.feature.reminderlist.navigateToReminderList
import com.ernestschcneider.feature.reminderlist.reminderListScreen
import com.ernestschcneider.feature.remindernote.navigateToReminderNote
import com.ernestschcneider.feature.remindernote.reminderNoteScreen
import com.ernestschcneider.feature.reminders.remindersScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val activity = LocalContext.current as Activity
    val noReminderId = " "
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = REMINDERS_ROUTE
    ) {
        remindersScreen(
            onNavigateUp = { activity.finish() },
            onReminderClick = { reminderId ->
                navController.navigateToReminderNote(reminderId)
            },
            onReminderCreationClick = { ->
                navController.navigateToReminderNote(noReminderId)
            },
            onListReminderCreationClick = {
                navController.navigateToReminderList(noReminderId)
            },
            onListReminderClick = { reminderListId ->
                navController.navigateToReminderList(reminderListId)
            }
        )
        reminderNoteScreen(
            onNavigateUp = { navController.navigateUp() }
        )
        reminderListScreen(
            onNavigateUp = { navController.navigateUp()}
        )
    }
}

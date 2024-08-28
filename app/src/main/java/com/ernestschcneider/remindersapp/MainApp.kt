package com.ernestschcneider.remindersapp

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ernestschcneider.feature.remiderdetails.remindersDetailScreen
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
        startDestination = "reminders"
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
                println("List reminder creation clicked")
            },
            onListReminderClick = {
                println("List reminder clicked")
            }
        )

        remindersDetailScreen(
            onNavigateUp = { navController.navigateUp() }
        )

        reminderNoteScreen(
            onNavigateUp = { navController.navigateUp() }
        )

        reminderListScreen(
            onNavigateUp = { navController.navigateUp()}
        )
    }
}

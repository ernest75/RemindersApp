package com.ernestschcneider.remindersapp

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ernestschcneider.feature.remiderdetails.remindersDetailScreen
import com.ernestschcneider.feature.remindernote.navigateToReminderNoteDetail
import com.ernestschcneider.feature.remindernote.noteCreationScreen
import com.ernestschcneider.feature.reminders.remindersScreen
import com.ernestschcneider.models.ReminderType

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
                navController.navigateToReminderNoteDetail(reminderId)
            },
            onReminderCreationClick = { ->
                navController.navigateToReminderNoteDetail(noReminderId)
            },
            onListReminderCreationClick = {
                // TODO change for new navigation fun
                navController.navigateToReminderNoteDetail(noReminderId)
            },
            onListReminderClick = {
                println("List reminder clicked")
            }
        )

        remindersDetailScreen(
            onNavigateUp = { navController.navigateUp() }
        )

        noteCreationScreen(
            onNavigateUp = { navController.navigateUp() }
        )
    }
}

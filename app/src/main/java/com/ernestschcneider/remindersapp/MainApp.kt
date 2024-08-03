package com.ernestschcneider.remindersapp

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ernestschcneider.feature.remiderdetails.navigateToReminderDetailsScreen
import com.ernestschcneider.feature.remiderdetails.remindersDetailScreen
import com.ernestschcneider.feature.remindercreation.notecreation.navigateToNoteCreation
import com.ernestschcneider.feature.remindercreation.notecreation.noteCreationScreen
import com.ernestschcneider.feature.reminders.remindersScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val activity = LocalContext.current as Activity
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = "reminders"
    ) {
        remindersScreen(
            onNavigateUp = { activity.finish() },
            onReminderClick = { reminderId ->
                navController.navigateToReminderDetailsScreen(reminderId)
            },
            onNoteCreationClick = {
                navController.navigateToNoteCreation()
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

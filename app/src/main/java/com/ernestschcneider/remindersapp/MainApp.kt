package com.ernestschcneider.remindersapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ernestschcneider.feature.remiderdetails.navigateToReminderDetailsScreen
import com.ernestschcneider.feature.remiderdetails.remindersDetailScreen
import com.ernestschcneider.feature.reminders.navigateToReminders
import com.ernestschcneider.feature.reminders.remindersScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = "reminders"
    ){
        remindersScreen(
            onReminderDetail = {
                navController.navigateToReminderDetailsScreen()
            }
        )

        remindersDetailScreen(
            onNavigateUp = { navController.navigateUp() }
        )
    }
}

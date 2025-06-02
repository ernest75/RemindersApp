package com.ernestschcneider.feature.reminders.useaces

import com.ernestschcneider.remindersapp.models.Reminder
import com.ernestschcneider.remindersapp.local.StorageRepo
import javax.inject.Inject

class RemoveReminderUseCase @Inject constructor(private val localRepo: StorageRepo) {
    suspend operator fun invoke(reminder: Reminder) = localRepo.deleteReminder(reminder)
}
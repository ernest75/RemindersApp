package com.ernestschcneider.feature.reminders.useaces

import com.ernestschcneider.remindersapp.local.StorageRepo
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(private val localRepo: StorageRepo) {
    suspend operator fun invoke() = localRepo.getAllReminders()
}

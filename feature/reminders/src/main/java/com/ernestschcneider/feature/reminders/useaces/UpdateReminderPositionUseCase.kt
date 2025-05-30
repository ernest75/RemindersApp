package com.ernestschcneider.feature.reminders.useaces

import com.ernestschcneider.remindersapp.local.StorageRepo
import javax.inject.Inject

class UpdateReminderPositionUseCase @Inject constructor(private val localRepo: StorageRepo) {
    suspend operator fun invoke(index: Int, reminderId: String) =
        localRepo.updateReminderPosition(index, reminderId)
}
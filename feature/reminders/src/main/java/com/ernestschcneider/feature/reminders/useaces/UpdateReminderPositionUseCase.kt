package com.ernestschcneider.feature.reminders.useaces

import com.ernestschcneider.remindersapp.local.LocalRepo
import javax.inject.Inject

class UpdateReminderPositionUseCase @Inject constructor(private val localRepo: LocalRepo) {
    suspend operator fun invoke(index: Int, reminderId: String) =
        localRepo.updateReminderPosition(index, reminderId)
}

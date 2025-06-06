package com.ernestschcneider.remindersapp.data.usecases

import com.ernestschcneider.remindersapp.local.LocalRepo
import javax.inject.Inject

class GetReminderUseCase @Inject constructor(
    private val reminderRepository: LocalRepo
) {
    suspend operator fun invoke(reminderId: String) = reminderRepository.getReminder(reminderId)
}


package com.ernestschcneider.remindersapp.data.usecases

import com.ernestschcneider.remindersapp.local.LocalRepo
import com.ernestschcneider.remindersapp.models.Reminder
import javax.inject.Inject

class SaveReminderUseCase @Inject constructor(
    private val reminderRepository: LocalRepo
) {
    suspend operator fun invoke(reminder: Reminder){
        reminderRepository.saveReminder(reminder)
    }
}

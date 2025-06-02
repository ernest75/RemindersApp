package com.ernestschcneider.remindersapp.data.usecases

import com.ernestschcneider.remindersapp.local.LocalRepo
import javax.inject.Inject

class CountRemindersUseCase @Inject constructor(
    private val reminderRepository: LocalRepo
) {
    suspend operator fun invoke(): Int = reminderRepository.countReminders()
}
package com.ernestschcneider.feature.reminders.useaces

import com.ernestschcneider.remindersapp.local.LocalRepo
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(private val localRepo: LocalRepo) {
    suspend operator fun invoke() = localRepo.getAllReminders()
}
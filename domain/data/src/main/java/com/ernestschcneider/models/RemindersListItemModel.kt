package com.ernestschcneider.models

import com.ernestschcneider.DEFAULT_UUID

data class RemindersListItemModel(
    val id: String = DEFAULT_UUID,
    val reminderContent: String
)

package com.ernestschcneider.models

data class ReminderListItem(
    val position: Int = -1,
    val text: String = "",
    val isCrossed: Boolean = false
)

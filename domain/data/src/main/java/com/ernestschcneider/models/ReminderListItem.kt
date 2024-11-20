package com.ernestschcneider.models

data class ReminderListItem(
    var position: Int = -1,
    val text: String = "",
    val isCrossed: Boolean = false
)

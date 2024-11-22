package dev.playground.persistence.model

import dev.playground.persistence.data.Priority
import java.time.LocalDateTime

/**
 * allowing properties to be nullable (for patch request)
 */
data class TaskUpdateRequest(
    val description: String?,
    val isReminderSet: Boolean?,
    val isTaskOpen: Boolean?,
    val createdOn: LocalDateTime?,
    val priority: Priority?,
)

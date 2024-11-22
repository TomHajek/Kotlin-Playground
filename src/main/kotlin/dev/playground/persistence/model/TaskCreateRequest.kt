package dev.playground.persistence.model

import dev.playground.persistence.data.Priority
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class TaskCreateRequest(
    @NotBlank(message = "Task description can't be empty")
    val description: String,

    val isReminderSet: Boolean,
    val isTaskOpen: Boolean,

    @NotBlank(message = "Task creation timestamp can't be empty")
    val createdOn: LocalDateTime,

    val priority: Priority,
)

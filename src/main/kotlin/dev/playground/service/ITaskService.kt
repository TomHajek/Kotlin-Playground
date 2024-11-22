package dev.playground.service

import dev.playground.persistence.model.TaskCreateRequest
import dev.playground.persistence.model.TaskDto
import dev.playground.persistence.model.TaskUpdateRequest

interface ITaskService {
    fun getTaskById(id: Long): TaskDto
    fun getAllTasks(): List<TaskDto>
    fun getAllOpenTasks(): List<TaskDto>
    fun getAllClosedTasks(): List<TaskDto>
    fun createTask(request: TaskCreateRequest): TaskDto
    fun updateTask(id: Long, request: TaskUpdateRequest): TaskDto
    fun deleteTask(id: Long): String
}
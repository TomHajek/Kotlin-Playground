package dev.playground.controller

import dev.playground.persistence.model.TaskCreateRequest
import dev.playground.persistence.model.TaskDto
import dev.playground.persistence.model.TaskUpdateRequest
import dev.playground.service.ITaskService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/task")
class TaskController(private val taskService: ITaskService) {
    
    @GetMapping
    fun getAllTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity(taskService.getAllTasks(), HttpStatus.OK)
    
    @GetMapping("/open")
    fun getAllOpenTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity(
        taskService.getAllOpenTasks(), HttpStatus.OK
    )

    @GetMapping("/closed")
    fun getAllClosedTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity(
        taskService.getAllClosedTasks(), HttpStatus.OK
    )
    
    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<TaskDto> = ResponseEntity(
        taskService.getTaskById(id), HttpStatus.OK
    )
    
    @PostMapping
    fun createTask(@Valid @RequestBody request: TaskCreateRequest): ResponseEntity<TaskDto> = ResponseEntity(
        taskService.createTask(request),
        HttpStatus.CREATED
    )
    
    @PatchMapping("/{id}")
    fun updateTask(
        @PathVariable id: Long,
        @Valid @RequestBody request: TaskUpdateRequest
    ): ResponseEntity<TaskDto> = ResponseEntity(taskService.updateTask(id, request), HttpStatus.OK)
    
    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<String> = ResponseEntity(
        taskService.deleteTask(id),
        HttpStatus.NO_CONTENT
    )
    
}
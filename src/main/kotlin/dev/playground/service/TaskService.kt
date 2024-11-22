package dev.playground.service

import dev.playground.exception.BadRequestException
import dev.playground.exception.TaskNotFoundException
import dev.playground.persistence.data.Task
import dev.playground.persistence.model.TaskCreateRequest
import dev.playground.persistence.model.TaskDto
import dev.playground.persistence.model.TaskUpdateRequest
import dev.playground.persistence.repository.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field
import java.util.stream.Collectors
import kotlin.reflect.full.memberProperties

@Service
class TaskService(private val taskRepository: TaskRepository): ITaskService {
    
    override fun getTaskById(id: Long): TaskDto {
        checkTaskForId(id)
        val task: Task = taskRepository.findTaskById(id)
        return mappingEntityToDto(task)
    }
    
    override fun getAllTasks(): List<TaskDto> = taskRepository.findAll()
        .stream()
        .map(this::mappingEntityToDto)
        .collect(Collectors.toList())
    
    override fun getAllOpenTasks(): List<TaskDto> = taskRepository.queryAllOpenTasks()
        .stream()
        .map(this::mappingEntityToDto)
        .collect(Collectors.toList())
    
    override fun getAllClosedTasks(): List<TaskDto> = taskRepository.queryAllClosedTasks()
        .stream()
        .map(this::mappingEntityToDto)
        .collect(Collectors.toList())
    
    
    override fun createTask(request: TaskCreateRequest): TaskDto {
        if(taskRepository.doesDescriptionExist(request.description)) {
            throw BadRequestException("There is already a task with the description: ${request.description}")
        }
        
        val task = Task()
        mappingFromRequestToEntity(task, request)
        
        val savedTask = taskRepository.save(task)
        return mappingEntityToDto(savedTask)
    }
    
    override fun updateTask(id: Long, request: TaskUpdateRequest): TaskDto {
        checkTaskForId(id)
        val existingTask = taskRepository.findTaskById(id)
        
        // looping all the properties of the `TaskUpdateRequest`
        for(property in TaskUpdateRequest::class.memberProperties) {
            // check if the request property is not null
            if(property.get(request) != null) {
                // if there is an optional property(field) with given name
                val field: Field? = ReflectionUtils.findField(Task::class.java, property.name)
                // unwrapping the let method
                field?.let {
                    // checking if field is accessible
                    it.isAccessible = true
                    // set the field
                    ReflectionUtils.setField(it, existingTask, property.get(request))
                }
            }
        }
        
        val savedTask = taskRepository.save(existingTask)
        return mappingEntityToDto(savedTask)
    }
    
    override fun deleteTask(id: Long): String {
        checkTaskForId(id)
        taskRepository.deleteById(id)
        return "Task with id: $id has been deleted."
    }
    
    private fun checkTaskForId(id: Long) {
        if(!taskRepository.existsById(id)) {
            throw TaskNotFoundException("Task with id: $id does not exist!")
        }
    }
    
    // Kotlin's single line expression syntax
    private fun mappingEntityToDto(task: Task) = TaskDto(
        task.id,
        task.description,
        task.isReminderSet,
        task.isTaskOpen,
        task.createdOn,
        task.priority
    )
    
    // Standard block syntax
    private fun mappingFromRequestToEntity(task: Task, request: TaskCreateRequest) {
        task.description = request.description
        task.isReminderSet = request.isReminderSet
        task.isTaskOpen = request.isTaskOpen
        task.createdOn = request.createdOn
        task.priority = request.priority
    }
    
}
package dev.playground.controller

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dev.playground.persistence.data.Priority
import dev.playground.persistence.model.TaskDto
import dev.playground.service.TaskService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.playground.exception.TaskNotFoundException
import dev.playground.persistence.model.TaskCreateRequest
import dev.playground.persistence.model.TaskUpdateRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDateTime

/**
 * @WebMvcTest is for testing single or multiple MVC controllers;
 * it creates a mock environment including mock servlet, context and config.
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [TaskController::class])
class TaskControllerIntegrationTest(
    @Autowired private val mockMvc: MockMvc
) {
    
    @MockBean
    private lateinit var mockService: TaskService
    
    private val taskId: Long = 77
    private val dummyDto = TaskDto(
        id = 77,
        description = "integration testing",
        isReminderSet = true,
        isTaskOpen = true,
        createdOn = LocalDateTime.now(),
        priority = Priority.LOW
    )
    
    // for serializing and deserializing JSONs
    private val mapper =  jacksonObjectMapper()
    
    @BeforeEach
    fun setUp() {
        mapper.registerModule(JavaTimeModule())
        
    }
    
    
    
    @Test
    fun `given all tasks endpoint is called then check for number of tasks`() {
        // Given
        val taskDto = TaskDto(
            22,
            "buy hummus",
            false,
            false,
            LocalDateTime.now(),
            Priority.MEDIUM
        )
        val tasks = listOf(dummyDto, taskDto)
        
        // When
        `when`(mockService.getAllTasks()).thenReturn(listOf(dummyDto, taskDto))
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/task"))
        
        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().`is`(200))
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        resultActions.andExpect(jsonPath("$.size()").value(tasks.size))
    }
    
    @Test
    fun `when task id does not exist then except is not found response`() {
        `when`(mockService.getTaskById(taskId)).thenThrow(TaskNotFoundException("Task with id: $taskId does not exist!"))
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/task/{id}"))
        
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound)
    }
    
    @Test
    fun `when get task by id is called with an character in the url then expect a bad request message`() {
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/task/13L"))
        
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
    
    @Test
    fun `given create task request when task is created then check for the properties`() {
        val request = TaskCreateRequest(
            dummyDto.description,
            dummyDto.isReminderSet,
            dummyDto.isTaskOpen,
            LocalDateTime.now(),
            dummyDto.priority
        )
        
        `when`(mockService.createTask(request)).thenReturn(dummyDto)
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
        
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated)
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        resultActions.andExpect(jsonPath("$.description").value(dummyDto.description))
        resultActions.andExpect(jsonPath("$.isReminderSet").value(dummyDto.isReminderSet))
        resultActions.andExpect(jsonPath("$.isTaskOpen").value(dummyDto.isTaskOpen))
        resultActions.andExpect(jsonPath("$.priority").value(dummyDto.priority))
    }
    
    @Test
    fun `given update task when task is updated then check for correct properties`() {
        val request = TaskUpdateRequest(
            dummyDto.description,
            dummyDto.isReminderSet,
            dummyDto.isTaskOpen,
            LocalDateTime.now(),
            dummyDto.priority
        )
        
        `when`(mockService.updateTask(dummyDto.id, request)).thenReturn(dummyDto)
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders.patch("/api/task/${dummyDto.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
        
        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        resultActions.andExpect(jsonPath("$.description").value(dummyDto.description))
        resultActions.andExpect(jsonPath("$.isReminderSet").value(dummyDto.isReminderSet))
        resultActions.andExpect(jsonPath("$.isTaskOpen").value(dummyDto.isTaskOpen))
        resultActions.andExpect(jsonPath("$.priority").value(dummyDto.priority))
    }
    
    @Test
    fun `given id for delete request when task is deleted then check for the response message`() {
        val expectedMessage = "Task with id: $taskId has been deleted."
        
        `when`(mockService.deleteTask(taskId)).thenReturn(expectedMessage)
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/task/${dummyDto.id}")
        )
        
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent)
        resultActions.andExpect(content().string(expectedMessage))
    }
    
}
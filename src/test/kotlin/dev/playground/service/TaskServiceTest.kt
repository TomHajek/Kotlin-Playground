package dev.playground.service

import dev.playground.exception.BadRequestException
import dev.playground.exception.TaskNotFoundException
import dev.playground.persistence.data.Priority
import dev.playground.persistence.data.Task
import dev.playground.persistence.model.TaskCreateRequest
import dev.playground.persistence.model.TaskDto
import dev.playground.persistence.model.TaskUpdateRequest
import dev.playground.persistence.repository.TaskRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class TaskServiceTest {
    
    @RelaxedMockK
    private lateinit var mockRepository: TaskRepository
    
    @InjectMockKs
    private lateinit var objectUnderTest: TaskService
    
    private val task = Task()
    private val taskId: Long = 543
    private lateinit var createRequest: TaskCreateRequest
    
    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        createRequest = TaskCreateRequest(
            description = "test",
            isReminderSet = false,
            isTaskOpen = false,
            createdOn =  LocalDateTime.now(),
            priority = Priority.LOW
        )
    }
    
    @Test
    fun `when all tasks get fetched then check if given size is correct`() {
        // Given
        val expectedTasks: List<Task> = listOf(Task(), Task())
        
        // When
        every { mockRepository.findAll() } returns expectedTasks.toMutableList()
        val actualTasks: List<TaskDto> = objectUnderTest.getAllTasks()
        
        // Then
        assertThat(actualTasks.size).isEqualTo(expectedTasks.size)
    }
    
    @Test
    fun `when task is created then check for the task properties`() {
        // Given
        task.description = createRequest.description
        task.isTaskOpen = createRequest.isTaskOpen
        task.priority = createRequest.priority
        
        // When
        every { mockRepository.save(any()) } returns task
        val actualTaskDto: TaskDto = objectUnderTest.createTask(createRequest)
        
        // Then
        assertThat(actualTaskDto.description).isEqualTo(task.description)
        assertThat(actualTaskDto.isTaskOpen).isEqualTo(task.isTaskOpen)
        assertThat(actualTaskDto.priority).isEqualTo(task.priority)
    }
    
    @Test
    fun `when task description already exists then check for bad request exception`() {
        // Given
        // When
        every { mockRepository.doesDescriptionExist(any()) } returns true
        val exception = assertThrows<BadRequestException> {
            objectUnderTest.createTask(createRequest)
        }
        
        // Then
        assertThat(exception.message).isEqualTo(
            "There is already a task with the description: ${createRequest.description}"
        )
        verify { mockRepository.save(any()) wasNot called }
    }
    
    @Test
    fun `when get task by id is called then except a task not found exception`() {
        every { mockRepository.existsById(taskId) } returns false
        val exception = assertThrows<TaskNotFoundException> {
            objectUnderTest.getTaskById(taskId)
        }
        
        assertThat(exception.message).isEqualTo("Task with id: $taskId does not exist!")
    }
    
    @Test
    fun `when all open tasks are fetched check the property is task open is true`() {
        task.isTaskOpen = true
        val expectedTasks = listOf(task)
        
        every { mockRepository.queryAllOpenTasks() } returns expectedTasks.toMutableList()
        val actualList: List<TaskDto> = objectUnderTest.getAllOpenTasks()
        
        assertThat(actualList[0].isTaskOpen).isEqualTo(expectedTasks[0].isTaskOpen)
    }
    
    @Test
    fun `when all open tasks are fetched check the property is task open is false`() {
        task.isTaskOpen = false
        val expectedTasks = listOf(task)
        
        every { mockRepository.queryAllClosedTasks() } returns expectedTasks.toMutableList()
        val actualList: List<TaskDto> = objectUnderTest.getAllClosedTasks()
        
        assertThat(actualList[0].isTaskOpen).isEqualTo(expectedTasks[0].isTaskOpen)
    }
    
    @Test
    fun `when save task is called then check if argument could be captured`() {
        val taskSlot = slot<Task>()
        task.description = createRequest.description
        task.isTaskOpen = createRequest.isTaskOpen
        task.isReminderSet = createRequest.isReminderSet
        task.priority = createRequest.priority
        
        every { mockRepository.save(capture(taskSlot)) } returns task
        val actualTaskDto: TaskDto = objectUnderTest.createTask(createRequest)
        
        verify { mockRepository.save(capture(taskSlot)) }
        assertThat(taskSlot.captured.description).isEqualTo(actualTaskDto.description)
        assertThat(taskSlot.captured.isTaskOpen).isEqualTo(actualTaskDto.isTaskOpen)
        assertThat(taskSlot.captured.isReminderSet).isEqualTo(actualTaskDto.isReminderSet)
        assertThat(taskSlot.captured.priority).isEqualTo(actualTaskDto.priority)
    }
    
    @Test
    fun `when get task by id is called then check for a specific description`() {
        task.description = "buy hummus"
        
        every { mockRepository.existsById(any()) } returns true
        every { mockRepository.findTaskById(any()) } returns task
        val actualTaskDto: TaskDto = objectUnderTest.getTaskById(taskId)
        
        assertThat(actualTaskDto.description).isEqualTo(task.description)
    }
    
    @Test
    fun `when get task by id is called then check if argument could be captured`() {
        val taskIdSlot = slot<Long>()
        
        every { mockRepository.existsById(any()) } returns true
        every { mockRepository.findTaskById(capture(taskIdSlot)) } returns task
        objectUnderTest.getTaskById(taskId)
        
        verify { mockRepository.findTaskById(capture(taskIdSlot)) }
        assertThat(taskIdSlot.captured).isEqualTo(taskId)
    }
    
    @Test
    fun `when delete task is called then check the response message`() {
        every { mockRepository.existsById(any()) } returns true
        val actualMessage: String = objectUnderTest.deleteTask(taskId)
        
        assertThat(actualMessage).isEqualTo("Task with id: $taskId has been deleted.")
    }
    
    @Test
    fun `when delete task is called then check if argument could be captured`() {
        val taskIdSlot = slot<Long>()
        
        every { mockRepository.existsById(any()) } returns true
        every { mockRepository.deleteById(capture(taskIdSlot)) } returns Unit
        objectUnderTest.deleteTask(taskId)
        
        verify { mockRepository.deleteById(capture(taskIdSlot)) }
        assertThat(taskIdSlot.captured).isEqualTo(taskId)
    }
    
    @Test
    fun `when update task is called then check for the request properties`() {
        task.description = "go to restaurant"
        task.isReminderSet = false
        task.isTaskOpen = true
        task.priority = Priority.MEDIUM
        
        val request = TaskUpdateRequest(
            task.description,
            task.isReminderSet,
            task.isTaskOpen,
            createdOn = LocalDateTime.now(),
            task.priority
        )
        
        every { mockRepository.existsById(any()) } returns true
        every { mockRepository.findTaskById(any()) } returns task
        every { mockRepository.save(any()) } returns task
        objectUnderTest.updateTask(taskId, request)
        val actualTaskDto: TaskDto = objectUnderTest.updateTask(taskId, request)
        
        assertThat(actualTaskDto.description).isEqualTo(task.description)
        assertThat(actualTaskDto.isReminderSet).isEqualTo(task.isReminderSet)
        assertThat(actualTaskDto.isTaskOpen).isEqualTo(task.isTaskOpen)
        assertThat(actualTaskDto.priority).isEqualTo(task.priority)
    }
    
}
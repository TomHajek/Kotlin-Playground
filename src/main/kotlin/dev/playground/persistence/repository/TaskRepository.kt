package dev.playground.persistence.repository

import dev.playground.persistence.data.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<Task, Long> {

    // Implemented by the Spring FW
    fun findTaskById(id: Long): Task

    // Native queries
    @Query(value = "SELECT * FROM task WHERE is_task_open = TRUE", nativeQuery = true)
    fun queryAllOpenTasks(): List<Task>

    @Query(value = "SELECT * FROM task WHERE is_task_open = FALSE", nativeQuery = true)
    fun queryAllClosedTasks(): List<Task>

    /*
     *  This method checks if there is any task that has the description of the given argument.
     *  If there is any task with the given description, then the method returns true otherwise
     *  false. This query is a JPA(JPQL) query.
     */
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Task t WHERE t.description =? 1")
    fun doesDescriptionExist(description: String): Boolean

}
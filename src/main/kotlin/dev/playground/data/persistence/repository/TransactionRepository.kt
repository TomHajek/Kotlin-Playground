package dev.playground.data.persistence.repository

import dev.playground.data.persistence.entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findByAccountId(id: Long): List<Transaction>
    
}
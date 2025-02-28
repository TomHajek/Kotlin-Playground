package dev.playground.data.service

import dev.playground.data.mapper.Mapper.toDto
import dev.playground.data.persistence.dto.TransactionDto
import dev.playground.data.persistence.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val repository: TransactionRepository
) : TransactionService {
    
    override fun getTransactions(): List<TransactionDto> =
        repository.findAll().map { it.toDto() }
    
    override fun getTransactionsByAccountId(id: Long): List<TransactionDto> =
        repository.findByAccountId(id).map { it.toDto() }
    
}
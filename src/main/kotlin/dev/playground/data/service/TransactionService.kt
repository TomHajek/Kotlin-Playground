package dev.playground.data.service

import dev.playground.data.persistence.dto.TransactionDto

interface TransactionService {
    fun getTransactions(): List<TransactionDto>
    fun getTransactionsByAccountId(id: Long): List<TransactionDto>
}
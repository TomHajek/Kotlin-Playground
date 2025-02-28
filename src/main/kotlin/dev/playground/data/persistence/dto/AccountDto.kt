package dev.playground.data.persistence.dto

import dev.playground.data.persistence.entity.Currency
import java.math.BigDecimal

data class AccountDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val balance: BigDecimal,
    val currency: Currency,
    val transactions: List<TransactionDto>
)
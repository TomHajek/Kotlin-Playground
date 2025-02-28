package dev.playground.data.persistence.dto

import dev.playground.data.persistence.entity.Currency
import java.math.BigDecimal

data class TransactionDto(
    val id: Long,
    val accountId: Long,
    val amount: BigDecimal,
    val currency: Currency,
    val description: String,
)
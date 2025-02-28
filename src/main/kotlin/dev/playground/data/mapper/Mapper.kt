package dev.playground.data.mapper

import dev.playground.data.persistence.dto.AccountDto
import dev.playground.data.persistence.dto.TransactionDto
import dev.playground.data.persistence.entity.Account
import dev.playground.data.persistence.entity.Transaction

object Mapper {
    
    fun Transaction.toDto(): TransactionDto = TransactionDto(
        id = this.id,
        accountId = this.account.id,
        amount = this.amount,
        currency = this.currency,
        description = this.description
    )
    
    fun Account.toDto(): AccountDto = AccountDto(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        balance = this.balance,
        currency = this.currency,
        transactions = this.transactions.map { it.toDto() }
    )
    
}
package dev.playground.data.service

import dev.playground.data.persistence.dto.AccountDto

interface AccountService {
    fun getAccounts(): List<AccountDto>
    fun getAccountById(id: Long): AccountDto
}
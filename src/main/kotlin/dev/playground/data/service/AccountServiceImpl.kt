package dev.playground.data.service

import dev.playground.data.mapper.Mapper.toDto
import dev.playground.data.persistence.dto.AccountDto
import dev.playground.data.persistence.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
    private val repository: AccountRepository
) : AccountService {
    
    override fun getAccounts(): List<AccountDto> =
        repository.findAll().map { it.toDto() }
    
    override fun getAccountById(id: Long): AccountDto =
        repository.findById(id)
            .map { it.toDto() }
            .orElseThrow { NoSuchElementException("Account with id $id not found") }
        
}
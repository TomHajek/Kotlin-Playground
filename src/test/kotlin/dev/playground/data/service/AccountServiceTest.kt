package dev.playground.data.service

import dev.playground.data.persistence.entity.Account
import dev.playground.data.persistence.entity.Currency
import dev.playground.data.persistence.repository.AccountRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class AccountServiceTest {
    
    private val repository: AccountRepository = mockk()
    private val accountService = AccountServiceImpl(repository)
    
    @Test
    fun `should return all accounts`() {
        // Given
        val accounts = listOf(
            Account(1L, "John", "Doe", "john.doe@example.com", BigDecimal(5000), Currency.USD),
            Account(2L, "Jane", "Smith", "jane.smith@example.com", BigDecimal(10000), Currency.GBP)
        )
        every { repository.findAll() } returns accounts
        
        // When
        val result = accountService.getAccounts()
        
        // Then
        assertEquals(2, result.size)
        assertEquals("John", result[0].firstName)
        assertEquals("Jane", result[1].firstName)
        verify(exactly = 1) { repository.findAll() }
    }
    
    @Test
    fun `should return account by id`() {
        val account = Account(1L, "John", "Doe", "john.doe@example.com", BigDecimal(5000), Currency.USD)
        every { repository.findById(1L) } returns Optional.of(account)
        
        val result = accountService.getAccountById(1L)
        
        assertNotNull(result)
        assertEquals("John", result.firstName)
        verify(exactly = 1) { repository.findById(1L) }
    }
    
    @Test
    fun `should throw exception when account not found`() {
        every { repository.findById(99L) } returns Optional.empty()
        
        val exception = assertThrows<NoSuchElementException> {
            accountService.getAccountById(99L)
        }
        
        assertEquals("Account with id 99 not found", exception.message)
        verify(exactly = 1) { repository.findById(99L) }
    }

}
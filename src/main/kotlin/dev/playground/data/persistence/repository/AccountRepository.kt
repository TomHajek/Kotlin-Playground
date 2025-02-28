package dev.playground.data.persistence.repository

import dev.playground.data.persistence.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    
    // just for the demo
    fun findByEmail(email: String)
    fun findByFirstNameAndLastName(firstName: String, lastName: String): List<Account>
    
}
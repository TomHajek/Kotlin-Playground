package dev.playground.persistence.repository

import dev.playground.persistence.data.Role
import dev.playground.persistence.data.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.*

/**
 * This is only for test and demo purpose
 */
@Repository
class UserRepo(
        private val encoder: PasswordEncoder
) {

    private val users = mutableListOf(
            User(
                    id = UUID.randomUUID(),
                    email = "email-1@gmail.com",
                    password = encoder.encode("pass1"),
                    role = Role.USER
            ),
            User(
                    id = UUID.randomUUID(),
                    email = "email-2@gmail.com",
                    password = encoder.encode("pass2"),
                    role = Role.ADMIN
            ),
            User(
                    id = UUID.randomUUID(),
                    email = "email-3@gmail.com",
                    password = encoder.encode("pass3"),
                    role = Role.USER
            )
    )

    fun save(user: User): Boolean {
        val updated = user.copy(password = encoder.encode(user.password))
        return users.add(updated)
    }

    fun findAll(): List<User> = users

    fun findByEmail(email: String): User? = users.firstOrNull {
        it.email == email
    }

    fun findByUUID(uuid: UUID): User? = users.firstOrNull {
        it.id == uuid
    }

    fun deleteByUUID(uuid: UUID): Boolean {
        val foundUser = findByUUID(uuid)
        return foundUser?.let {
            users.remove(it)
        } ?: false
    }

}
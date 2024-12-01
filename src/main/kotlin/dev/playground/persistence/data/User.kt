package dev.playground.persistence.data

import java.util.*

data class User(
        val id: UUID,
        val email: String,
        var password: String,
        val role: Role
) {}

enum class Role {
    USER, ADMIN
}
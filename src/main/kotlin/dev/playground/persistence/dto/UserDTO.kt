package dev.playground.persistence.dto

import dev.playground.persistence.data.Role
import java.util.*

data class UserDTO(
        val id: UUID,
        var email: String,
        var password: String,
        var role: Role
) {}
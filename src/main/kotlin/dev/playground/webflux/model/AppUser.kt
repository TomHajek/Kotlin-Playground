package dev.playground.webflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("app_user")
data class AppUser(
    @Id val id: Long? = null,
    val name: String,
    val email: String
)

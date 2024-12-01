package dev.playground.persistence.dto

data class AuthResponse(
        val accessToken: String,
        val refreshToken: String,
)

package dev.playground.persistence.dto

data class MovieDTO(
        val id: Int = -1,
        var name: String = "Default movie",
        var rating: Double
) {}
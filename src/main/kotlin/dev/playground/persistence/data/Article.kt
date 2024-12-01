package dev.playground.persistence.data

import java.util.UUID

data class Article(
        val id: UUID,
        val title: String,
        val content: String
) {}
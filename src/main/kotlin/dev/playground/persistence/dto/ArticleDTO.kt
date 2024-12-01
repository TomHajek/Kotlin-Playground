package dev.playground.persistence.dto

import java.util.*

data class ArticleDTO(
    val id: UUID,
    val title: String,
    val content: String
) {}
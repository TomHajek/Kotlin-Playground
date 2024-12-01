package dev.playground.persistence.repository

import dev.playground.persistence.data.Article
import org.springframework.stereotype.Repository
import java.util.*

/**
 * This is only for test and demo purpose
 */
@Repository
class ArticleRepo {

    private val articles = listOf(
            Article(id = UUID.randomUUID(), title = "Article 1", content = "Content 1"),
            Article(id = UUID.randomUUID(), title = "Article 2", content = "Content 2")
    )

    fun findAll(): List<Article> = articles

}
package dev.playground.service

import dev.playground.persistence.dto.ArticleDTO
import java.util.*

interface ArticleService {

    fun createArticle(articleDTO: ArticleDTO): ArticleDTO
    fun updateArticle(articleDTO: ArticleDTO): ArticleDTO
    fun deleteArticle(id: UUID): Boolean
    fun getArticle(id: UUID): ArticleDTO
    fun getArticles(): List<ArticleDTO>

}
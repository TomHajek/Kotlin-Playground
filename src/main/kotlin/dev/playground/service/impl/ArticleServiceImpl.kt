package dev.playground.service.impl

import dev.playground.mapper.ArticleMapper
import dev.playground.persistence.dto.ArticleDTO
import dev.playground.persistence.data.Article
import dev.playground.persistence.repository.ArticleRepo
import dev.playground.service.ArticleService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleServiceImpl(
    private val articleRepo: ArticleRepo,
    private val articleMapper: ArticleMapper
): ArticleService {

    override fun createArticle(articleDTO: ArticleDTO): ArticleDTO {
        TODO("Not yet implemented")
    }

    override fun updateArticle(articleDTO: ArticleDTO): ArticleDTO {
        TODO("Not yet implemented")
    }

    override fun deleteArticle(id: UUID) : Boolean {
        TODO("Not yet implemented")
    }

    override fun getArticle(id: UUID): ArticleDTO {
        TODO("Not yet implemented")
    }

    override fun getArticles(): List<ArticleDTO> {
        val articles = articleRepo.findAll()
        val response = articles.map {
            articleMapper.fromEntity(it)
        }
        return response
    }

    // This is an alternative, how to use private functions to map objects
    private fun Article.toDto(): ArticleDTO =
            ArticleDTO(
                    id = this.id,
                    title = this.title,
                    content = this.content
            )
}
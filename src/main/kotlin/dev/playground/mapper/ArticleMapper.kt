package dev.playground.mapper

import dev.playground.persistence.dto.ArticleDTO
import dev.playground.persistence.data.Article
import org.springframework.stereotype.Component

@Component
class ArticleMapper: Mapper<ArticleDTO, Article> {

    override fun fromEntity(entity: Article): ArticleDTO {
        return ArticleDTO(
                entity.id,
                entity.title,
                entity.content
        )
    }

    override fun toEntity(domain: ArticleDTO): Article {
        return Article(
                domain.id,
                domain.title,
                domain.content
        )
    }

}
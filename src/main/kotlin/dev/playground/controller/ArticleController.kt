package dev.playground.controller

import dev.playground.persistence.dto.ArticleDTO
import dev.playground.service.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/article")
class ArticleController(
      private val articleService: ArticleService
) {

    @GetMapping
    fun listAll(): ResponseEntity<List<ArticleDTO>> {
        return ResponseEntity(articleService.getArticles(), HttpStatus.OK)
    }

}
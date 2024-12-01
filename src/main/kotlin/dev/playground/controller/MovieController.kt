package dev.playground.controller

import dev.playground.persistence.dto.MovieDTO
import dev.playground.service.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MovieController(
        private val movieService: MovieService
) {

    @PostMapping
    fun createMovie(movieDTO: MovieDTO): ResponseEntity<MovieDTO> {
        return ResponseEntity(movieService.createMovie(movieDTO), HttpStatus.CREATED)
    }

    @GetMapping
    fun getMovies(): ResponseEntity<List<MovieDTO>> = ResponseEntity.ok(movieService.getMovies())

    @GetMapping("/{id}")
    fun getMovie(@PathVariable id: Int) = ResponseEntity.ok(movieService.getMovie(id))

    @PutMapping
    fun updateMovie(@RequestBody movieDTO: MovieDTO): ResponseEntity<MovieDTO> {
        return ResponseEntity.ok(movieService.updateMovie(movieDTO))
    }

    @DeleteMapping("/{id}")
    fun deleteMovie(@PathVariable id: Int): ResponseEntity<Unit> {
        return ResponseEntity(movieService.deleteMovie(id), HttpStatus.NO_CONTENT)
    }

}
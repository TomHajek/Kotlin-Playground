package dev.playground.persistence.repository

import dev.playground.persistence.entity.Movie
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository: CrudRepository<Movie, Int> {

    @Query("SELECT m FROM Movie as m")
    fun getAllMovies(): List<Movie>

}
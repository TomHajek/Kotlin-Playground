package dev.playground.utils

import dev.playground.persistence.dto.MovieDTO
import dev.playground.persistence.entity.Movie
import org.springframework.stereotype.Component

@Component
class MovieMapper: Mapper<MovieDTO, Movie> {

    override fun fromEntity(entity: Movie): MovieDTO = MovieDTO(
            entity.id,
            entity.name,
            entity.rating
    )

    override fun toEntity(domain: MovieDTO): Movie = Movie(
            domain.id,
            domain.name,
            domain.rating
    )

}
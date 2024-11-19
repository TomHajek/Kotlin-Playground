package dev.playground.webflux.repository

import dev.playground.webflux.model.AppUser
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface AppUserRepository: ReactiveCrudRepository<AppUser, Long> {
    fun findByEmail(email: String): Mono<AppUser>
}
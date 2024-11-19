package dev.playground.webflux.service

import dev.playground.webflux.model.AppUser
import dev.playground.webflux.model.AppUserRequest
import dev.playground.webflux.model.BadRequestException
import dev.playground.webflux.model.NotFoundException
import dev.playground.webflux.repository.AppUserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Mono == one item
 * Flux == multiple items
 */
@Service
class AppUserService(private val appUserRepository: AppUserRepository) {
    fun findAll(): Flux<AppUser> = appUserRepository.findAll()

    fun findById(id: Long): Mono<AppUser> = appUserRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("User with id $id was not found!")))

    fun createUser(appUserRequest: AppUserRequest): Mono<AppUser> {
        return findByEmailOrError(appUserRequest).switchIfEmpty(
                appUserRepository.save(
                        AppUser(
                                name = appUserRequest.name,
                                email = appUserRequest.email
                        )
                )
        )
    }

    fun updateUser(id: Long, appUserRequest: AppUserRequest): Mono<AppUser> {
        return findById(id).flatMap {
            findByEmailOrError(appUserRequest).switchIfEmpty(
                    appUserRepository.save(
                            AppUser(
                                    id = id,
                                    name = appUserRequest.name,
                                    email = appUserRequest.email
                            )
                    )
            )
        }
    }

    fun deleteUser(id: Long): Mono<Void> = findById(id).flatMap {
        appUserRepository.deleteById(id)
    }

    private fun findByEmailOrError(appUserRequest: AppUserRequest) =
            appUserRepository.findByEmail(appUserRequest.email)
                    .flatMap<AppUser?> {
                        Mono.error<AppUser>(
                                BadRequestException("User with email ${appUserRequest.email} already exists.")
                        )
                    }

}

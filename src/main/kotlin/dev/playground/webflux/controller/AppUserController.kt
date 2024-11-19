package dev.playground.webflux.controller

import dev.playground.webflux.model.AppUser
import dev.playground.webflux.model.AppUserRequest
import dev.playground.webflux.service.AppUserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class AppUserController(
    private val userService: AppUserService
) {
    @GetMapping("/user")
    fun getAll(): Flux<AppUser> = userService.findAll()

    @GetMapping("/user/{id}")
    fun getById(@PathVariable("id") id: Long): Mono<AppUser> = userService.findById(id)

    @PostMapping("/user")
    fun createUser(@Valid @RequestBody appUserRequest: AppUserRequest): Mono<AppUser> =
            userService.createUser(appUserRequest)

    @PutMapping("/user/{id}")
    fun updateUser(
            @PathVariable("id") id: Long,
            @Valid @RequestBody appUserRequest: AppUserRequest
    ): Mono<AppUser> = userService.updateUser(id, appUserRequest)

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable("id") id: Long): Mono<Void> = userService.deleteUser(id)

}
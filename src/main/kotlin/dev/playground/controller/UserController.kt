package dev.playground.controller

import dev.playground.persistence.dto.UserDTO
import dev.playground.persistence.data.User
import dev.playground.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
@RequestMapping("/api/user")
class UserController(
        private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        //return userService.createUser(userDTO)
        //        ?.toDto()
        //        ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create user!")

        return ResponseEntity(userService.createUser(userDTO), HttpStatus.CREATED)
    }

    @DeleteMapping("/{uuid}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Boolean> {
        val success = userService.deleteUser(id)

        return if (success)
            ResponseEntity.noContent().build()
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find user!")
    }

    @GetMapping("/{uuid}")
    fun getUser(@PathVariable id: UUID): ResponseEntity<UserDTO> =
            ResponseEntity(userService.getUser(id), HttpStatus.OK)

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserDTO>> {
        return ResponseEntity(userService.getUsers(), HttpStatus.OK)
    }

    // Helper methods can be an alternative for mapper
    private fun User.toDto(): UserDTO =
            UserDTO(
                    id = this.id,
                    email = this.email,
                    password = this.password,
                    role = this.role
            )

    private fun UserDTO.toEntity(): User =
            User(
                    id = this.id,
                    email = this.email,
                    password = this.password,
                    role = this.role
            )

}
package dev.playground.service

import dev.playground.persistence.dto.UserDTO
import java.util.*

interface UserService {

    fun createUser(userDTO: UserDTO): UserDTO
    fun updateUser(userDTO: UserDTO): UserDTO
    fun deleteUser(id: UUID): Boolean
    fun getUser(id: UUID): UserDTO
    fun getUsers(): List<UserDTO>

}
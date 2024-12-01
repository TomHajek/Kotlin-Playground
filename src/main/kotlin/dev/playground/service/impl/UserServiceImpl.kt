package dev.playground.service.impl

import dev.playground.mapper.UserMapper
import dev.playground.persistence.dto.UserDTO
import dev.playground.persistence.data.User
import dev.playground.persistence.repository.UserRepo
import dev.playground.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepo: UserRepo,
    private val userMapper: UserMapper,
    private val encoder: PasswordEncoder
): UserService {

    override fun createUser(userDTO: UserDTO): UserDTO {
        val found = userRepo.findByEmail(userDTO.email)

        return if (found == null) {
            // Encode the password and set it in userDTO
            val encodedPassword = encoder.encode(userDTO.password)
            val userToSave = userMapper.toEntity(userDTO).apply {
                // Using the property setter to set encoded password
                this.password = encodedPassword
            }

            userRepo.save(userToSave)
            userDTO.password = encodedPassword // Optional: updating the DTO if needed
            userDTO
        } else {
            userMapper.fromEntity(found)
        }
    }

    override fun updateUser(userDTO: UserDTO): UserDTO {
        val exists = userRepo.findByUUID(userDTO.id)

        if (exists != null) {
            exists.password = encoder.encode(userDTO.password)
            userRepo.save(exists)
            return userMapper.fromEntity(exists)
        } else {
            throw Exception("User not found!")
        }
    }

    override fun deleteUser(id: UUID): Boolean =
            userRepo.deleteByUUID(id)

    override fun getUser(id: UUID): UserDTO {
        val user = findByUUID(id)
        //return userMapper.fromEntity()
        TODO("Not yet implemented")
    }

    override fun getUsers(): List<UserDTO> = userRepo.findAll().map {
        userMapper.fromEntity(it)
    }

    private fun findByEmail(email: String): User? =
            userRepo.findByEmail(email)

    private fun findByUUID(uuid: UUID): User? =
            userRepo.findByUUID(uuid)

}
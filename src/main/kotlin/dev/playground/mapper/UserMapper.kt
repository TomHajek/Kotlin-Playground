package dev.playground.mapper

import dev.playground.persistence.dto.UserDTO
import dev.playground.persistence.data.User
import org.springframework.stereotype.Component

@Component
class UserMapper: Mapper<UserDTO, User> {

    override fun fromEntity(entity: User): UserDTO {
        return UserDTO(
                entity.id,
                entity.email,
                entity.password,
                entity.role
        )
    }

    override fun toEntity(domain: UserDTO): User {
        return User(
                domain.id,
                domain.email,
                domain.password,
                domain.role
        )
    }

}
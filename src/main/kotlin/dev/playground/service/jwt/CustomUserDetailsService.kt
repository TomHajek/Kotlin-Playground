package dev.playground.service.jwt

import dev.playground.persistence.repository.UserRepo
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

// Kotlin feature to avoid clash of Spring Security User and our User
typealias ApplicationUser = dev.playground.persistence.data.User

@Service
class CustomUserDetailsService(
        private val userRepo: UserRepo,
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
            userRepo.findByEmail(username)
                    ?.mapToUserDetails()
                    ?: throw UsernameNotFoundException("User not found!")

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
            User.builder()
                    .username(this.email)
                    .password(this.password)
                    .roles(this.role.name)
                    .build()

}
package dev.playground.configuration

import dev.playground.persistence.repository.UserRepo
import dev.playground.service.jwt.CustomUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {

    // Specifying our customUserService, which will be used in our DaoAuthenticationProvider
    @Bean
    fun userDetailsService(userRepo: UserRepo): UserDetailsService = CustomUserDetailsService(userRepo)

    // Specifying password encoder, which will be used in our DaoAuthenticationProvider
    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(userRepo: UserRepo): AuthenticationProvider =
            DaoAuthenticationProvider()
                    .also {
                        it.setUserDetailsService(userDetailsService(userRepo))
                        it.setPasswordEncoder(encoder())
            }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager

}
package dev.playground.service.jwt

import dev.playground.configuration.JwtProperties
import dev.playground.persistence.dto.AuthRequest
import dev.playground.persistence.dto.AuthResponse
import dev.playground.persistence.repository.RefreshTokenRepo
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepo: RefreshTokenRepo,
) {

    fun authentication(authRequest: AuthRequest): AuthResponse {
        // authenticate
        authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        authRequest.email,
                        authRequest.password
                )
        )

        // load user
        val user = userDetailsService.loadUserByUsername(authRequest.email)

        // generate the token
        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)

        refreshTokenRepo.save(refreshToken, user)

        // return token in api
        return AuthResponse(
                accessToken = accessToken,
                refreshToken = refreshToken
        )

    }

    fun refreshAccessToken(token: String): String? {
        val extractedEmail = tokenService.extractEmail(token)
        return extractedEmail?.let { email ->
            val currentUserDetails = userDetailsService.loadUserByUsername(email)
            val refreshTokenUserDetails = refreshTokenRepo.findUserDetailsByToken(token)

            if (tokenService.isExpired(token) &&
                    currentUserDetails.username == refreshTokenUserDetails?.username) {
                generateAccessToken(currentUserDetails)
            } else {
                null
            }
        }
    }

    /*
     * The content of these private methods was selected and then ctrl+alt+m and intellij will
     * automatically create a private method out of it for you
     */
    private fun generateRefreshToken(user: UserDetails) = tokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )

    private fun generateAccessToken(user: UserDetails) = tokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
    )

}
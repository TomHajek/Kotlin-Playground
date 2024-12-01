package dev.playground.controller

import dev.playground.persistence.dto.AuthRequest
import dev.playground.persistence.dto.AuthResponse
import dev.playground.persistence.dto.RefreshTokenRequest
import dev.playground.persistence.dto.TokenResponse
import dev.playground.service.jwt.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/auth")
class AuthController(
        private val authService: AuthService
) {

    @PostMapping
    fun authenticate(@RequestBody authRequest: AuthRequest): AuthResponse =
            authService.authentication(authRequest)

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshTokenRequest): TokenResponse =
            authService.refreshAccessToken(request.token)
                    ?.mapToTokenResponse()
                    ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token!")

    private fun String.mapToTokenResponse(): TokenResponse =
            TokenResponse(
                    token = this
            )

}
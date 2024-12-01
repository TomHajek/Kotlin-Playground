package dev.playground.service.jwt

import dev.playground.configuration.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

/**
 * In IntelliJ, we can add environment variables:
 * - copy 'JWT_KEY' from application.yaml
 * - go to Run/Debug configuration
 * - edit configuration
 * - in environment variables, specify somem for ex.: JWT_KEY=asjaosjoacjhysdvygav
 */
@Service
class TokenService(
        jwtProperties: JwtProperties
) {

    // Secret key is used to generate the tokens
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.key.toByteArray())

    // Function to generate access and refresh token
    fun generate(
            userDetails: UserDetails,
            expirationDate: Date,
            additionalClaims: Map<String, Any> = emptyMap()
    ): String =
            Jwts.builder()
                    .claims()
                    .subject(userDetails.username)
                    .issuedAt(Date(System.currentTimeMillis()))
                    .expiration(expirationDate)
                    .add(additionalClaims)
                    .and()
                    .signWith(secretKey)
                    .compact()

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)

        return userDetails.username == email && !isExpired(token)
    }

    fun isExpired(token: String): Boolean = getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))

    fun extractEmail(token: String): String? = getAllClaims(token).subject

    // Function to get all claims
    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
                // here we are checking if token is signed with the same key
                .verifyWith(secretKey)
                .build()

        // extracting the payload
        return parser
                .parseSignedClaims(token)
                .payload
    }

}
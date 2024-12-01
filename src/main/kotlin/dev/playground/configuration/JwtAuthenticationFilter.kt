package dev.playground.configuration

import dev.playground.service.jwt.CustomUserDetailsService
import dev.playground.service.jwt.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService
): OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {
        // Check the request if it contains authorization header
        val authHeader: String? = request.getHeader("Authorization")

        // Bearer
        if (authHeader.doesNotContainBearerToken()) {
            filterChain.doFilter(request, response)
            return
        }

        // Extract JWT
        val jwtToken = authHeader!!.extractTokenValue() // with '!!' we making sure it is not null
        val email = tokenService.extractEmail(jwtToken)

        // Check if there is no present authentication
        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            // try to find the user
            val foundUser = userDetailsService.loadUserByUsername(email)

            // verify the token, and update the context with our principal
            if (tokenService.isValid(jwtToken, foundUser)) {
                updateContext(foundUser, request)
            }

            filterChain.doFilter(request, response)
        }
    }

    // Extension (helper) functions
    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }

    private fun String.extractTokenValue(): String =
            this.substringAfter("Bearer ")

    private fun String?.doesNotContainBearerToken(): Boolean =
            this == null || !this.startsWith("Bearer ")

}
package org.example.sodam.global.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import org.example.sodam.global.jwt.auth.AuthDetailsService
import org.example.sodam.global.jwt.exception.ExpiredTokenException
import org.sodam.global.jwt.dto.TokenResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.util.Date
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService
) {
    companion object {
        private const val ACCESS_KEY = "access_token"
    }

    fun getToken(name: String): TokenResponse {
        val accessToken = generateAccessToken(name, ACCESS_KEY, jwtProperties.accessExp)

        return TokenResponse(accessToken)
    }

    private fun generateAccessToken(name: String, type: String, expiration: Long): String {
        return Jwts.builder()
            .setSubject(name)
            .setHeaderParam("type", type)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration * 1000))
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearer = request.getHeader("Authorization")
        return parseToken(bearer)
    }

    fun parseToken(bearerToken: String?): String? {
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.removePrefix("Bearer ")
        } else {
            null
        }
    }

    fun authorization(token: String): UsernamePasswordAuthenticationToken {
        val userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getTokenSubject(token: String): String {
        return getTokenBody(token).subject
    }

    private fun getTokenBody(token: String?): Claims {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException
        }
    }
}
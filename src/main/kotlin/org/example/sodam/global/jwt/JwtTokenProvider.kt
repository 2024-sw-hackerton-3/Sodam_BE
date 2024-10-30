package org.example.sodam.global.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.example.sodam.global.jwt.auth.AuthDetailsService
import org.example.sodam.global.jwt.exception.ExpiredTokenException
import org.sodam.global.jwt.dto.TokenResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService
) {
    companion object {
        private const val ACCESS_KEY = "access_token"
    }

    private val signingKey: Key = SecretKeySpec(jwtProperties.secretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName)

    fun getToken(name: String): TokenResponse {
        val accessToken = generateAccessToken(name, ACCESS_KEY, jwtProperties.accessExp)

        return TokenResponse(accessToken)
    }

    private fun generateAccessToken(id: String, type: String, exp: Long): String =
        Jwts.builder()
            .setSubject(id)
            .setHeaderParam("typ", type)
            .signWith(signingKey)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    fun resolveToken(request: HttpServletRequest): String? {
        val bearer = request.getHeader(jwtProperties.header)
        return parseToken(bearer)
    }

    fun parseToken(bearerToken: String?): String? {
        return if (bearerToken != null && bearerToken.startsWith(jwtProperties.prefix)) {
            bearerToken.removePrefix(jwtProperties.prefix)
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
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException
        }
    }
}

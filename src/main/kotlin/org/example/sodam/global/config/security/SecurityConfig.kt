package org.example.sodam.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.sodam.global.config.filter.FilterConfig
import org.example.sodam.global.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.web.cors.CorsUtils

@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf()
            .disable()
            .formLogin().and().cors()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests()
            .requestMatchers(CorsUtils::isCorsRequest)
            .permitAll().anyRequest().permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))

        http
            .apply(FilterConfig(objectMapper, jwtTokenProvider))

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}

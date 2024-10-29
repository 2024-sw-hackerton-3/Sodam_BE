package org.example.sodam

import org.example.sodam.global.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(JwtProperties::class)
@SpringBootApplication
class SodamBeApplication

fun main(args: Array<String>) {
    runApplication<SodamBeApplication>(*args)
}

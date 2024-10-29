package org.example.sodam.global.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "auth.jwt")
class JwtProperties(
    val secretKey: String,
    val accessExp: Long,
    val header: String,
    val prefix: String
)

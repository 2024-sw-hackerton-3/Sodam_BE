package org.example.sodam.global.jwt.exception

import org.example.sodam.global.error.exception.ErrorCode
import org.example.sodam.global.error.exception.SodamException

object ExpiredTokenException : SodamException(
    ErrorCode.EXPIRED_TOKEN
)

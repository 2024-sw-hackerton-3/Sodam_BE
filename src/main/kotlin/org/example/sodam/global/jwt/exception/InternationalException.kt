package org.example.sodam.global.jwt.exception

import org.example.sodam.global.error.exception.ErrorCode
import org.example.sodam.global.error.exception.SodamException

object InternationalException : SodamException(
    ErrorCode.INTERNAL_SERVER_ERROR
)

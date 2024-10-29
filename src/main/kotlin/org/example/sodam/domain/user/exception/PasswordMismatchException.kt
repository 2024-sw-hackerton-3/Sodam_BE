package org.example.sodam.domain.user.exception

import org.example.sodam.global.error.exception.ErrorCode
import org.example.sodam.global.error.exception.SodamException

object PasswordMismatchException : SodamException(
    ErrorCode.PASSWORD_MISMATCH
)

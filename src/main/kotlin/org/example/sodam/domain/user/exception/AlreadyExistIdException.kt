package org.example.sodam.domain.user.exception

import org.example.sodam.global.error.exception.ErrorCode
import org.example.sodam.global.error.exception.SodamException

object AlreadyExistIdException : SodamException(
    ErrorCode.ALREADY_EXIST_ID
)

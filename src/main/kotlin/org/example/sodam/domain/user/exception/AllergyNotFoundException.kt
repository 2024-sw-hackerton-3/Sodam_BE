package org.example.sodam.domain.user.exception

import org.example.sodam.global.error.exception.ErrorCode
import org.example.sodam.global.error.exception.SodamException

object AllergyNotFoundException : SodamException(
    ErrorCode.ALLERGY_NOT_FOUND
)

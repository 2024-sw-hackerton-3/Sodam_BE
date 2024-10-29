package org.example.sodam.global.error.exception

abstract class SodamException(
    val errorCode: ErrorCode
) : RuntimeException()

package org.example.sodam.domain.user.presentation

import org.example.sodam.domain.user.presentation.dto.request.SignUpRequest
import org.example.sodam.domain.user.service.UerSignUpService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userSignUpService: UerSignUpService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun signUp(@RequestBody request: SignUpRequest) = userSignUpService.signUp(request)
}

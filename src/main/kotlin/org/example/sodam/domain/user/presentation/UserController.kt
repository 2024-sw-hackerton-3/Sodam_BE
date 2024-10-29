package org.example.sodam.domain.user.presentation

import org.example.sodam.domain.user.presentation.dto.request.SignRequest
import org.example.sodam.domain.user.service.UerSignUpService
import org.example.sodam.domain.user.service.UserSignInService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userSignUpService: UerSignUpService,
    private val userSignInService: UserSignInService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignRequest) = userSignUpService.signUp(request)

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignRequest) = userSignInService.signIn(request)
}

package org.example.sodam.domain.user.presentation

import org.example.sodam.domain.user.presentation.dto.request.AllergyRequest
import org.example.sodam.domain.user.presentation.dto.request.SignInRequest
import org.example.sodam.domain.user.presentation.dto.request.SignUpRequest
import org.example.sodam.domain.user.service.AllergyService
import org.example.sodam.domain.user.service.PointUpService
import org.example.sodam.domain.user.service.UerSignUpService
import org.example.sodam.domain.user.service.UserSignInService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userSignUpService: UerSignUpService,
    private val userSignInService: UserSignInService,
    private val allergyService: AllergyService,
    private val pointUpService: PointUpService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest) = userSignUpService.signUp(request)

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignInRequest) = userSignInService.signIn(request)

    @PatchMapping("/allergy")
    fun allergy(@RequestBody request: AllergyRequest) = allergyService.allergies(request)

    @PatchMapping("point")
    fun pointUp(@RequestParam point: Int) = pointUpService.pointUp(point)

//    @GetMapping("/main")
}

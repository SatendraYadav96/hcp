package com.squer.promobee.api.v1

import com.squer.promobee.controller.UserController
import com.squer.promobee.security.jwt.JwtTokenProvider
import com.squer.promobee.service.UserService
import com.squer.promobee.validator.MapValidationErrorService
import com.squer.promobee.validator.UserValidator
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user")
@Tag(name = "V1 User APIs")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin
class UserApiV1(
    userService: UserService,
    mapValidationErrorService: MapValidationErrorService,
    userValidator: UserValidator,
    authenticationManager: AuthenticationManager,
    jwdTokenProvider: JwtTokenProvider,
    bCryptPasswordEncoder: BCryptPasswordEncoder,
) :
    UserController(
        userService = userService,
        userValidator = userValidator,
        mapValidationErrorService = mapValidationErrorService,
        authenticationManager = authenticationManager,
        jwdTokenProvider = jwdTokenProvider,
        bCryptPasswordEncoder = bCryptPasswordEncoder,
    )

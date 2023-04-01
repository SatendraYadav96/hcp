package com.squer.promobee.security.dto

import javax.validation.constraints.NotBlank

data class LoginRequest (
    @NotBlank(message = "Username cannot be blank")
    val username: String = "Invalid username",

    @NotBlank(message = "Password cannot be blank")
    val password: String = "Invalid password"
)

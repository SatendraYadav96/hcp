package com.squer.promobee.security.dto

data class LoginFailResponse (
    private val username: String = "Invalid username",
    private val password: String = "Invalid password"
)

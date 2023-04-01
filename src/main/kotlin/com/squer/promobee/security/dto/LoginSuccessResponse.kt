package com.squer.promobee.security.dto

data class LoginSuccessResponse(
    val success: Boolean? = false,
    val token: String?
)

package com.squer.promobee.security.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.security.Principal
import java.security.SecureRandom

@Component
class SecurityUtility {

    @Value("\${app.security.SALT_KEY}")
    private val SALT: String = "SALT_KEY" // Salt should be protected carefully

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(12, SecureRandom(SALT.toByteArray()))
    }

    fun getLoggedInPrincipal() : Principal? {
        return SecurityContextHolder.getContext().authentication
    }
}

package com.squer.promobee.security.service

import com.squer.promobee.security.domain.User
import org.springframework.security.core.userdetails.UserDetailsService

interface CustomUserDetailsService : UserDetailsService {
    fun loadUserById(id: String): User?
}

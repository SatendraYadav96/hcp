package com.squer.promobee.service.impl.impl

import com.squer.promobee.security.domain.User
import com.squer.promobee.security.repository.UserRepository
import com.squer.promobee.security.service.CustomUserDetailsService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@Slf4j
class CustomUserDetailsServiceImpl @Autowired constructor(
    private val userRepository: UserRepository
) : CustomUserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
    }

    override fun loadUserById(id: String): User {
        val user = userRepository.findById(id)
        if (user == null) throw UsernameNotFoundException("User not found")
        return user
    }
}

package com.squer.promobee.service.impl

import com.squer.promobee.exception.InvalidUserException
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.repository.SecurityRoleRepository
import com.squer.promobee.security.repository.UserRepository
import com.squer.promobee.service.UserService
import lombok.extern.slf4j.Slf4j
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
@Slf4j
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val userRoleRepository: SecurityRoleRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : UserService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${app.security.user.INITIAL_PASSWORD}")
    private val INITIAL_PASSWORD: String = "password"

    override fun saveUser(newUser: User): User {
        return try {
            // set encrypted password
            if (StringUtils.isBlank(newUser.password)) {
                newUser.password = bCryptPasswordEncoder.encode(INITIAL_PASSWORD) // default password
            } else {
                newUser.password = bCryptPasswordEncoder.encode(newUser.password) // encrypt password
            }
            userRepository.update(newUser)
            newUser
        } catch (e: Exception) {
            log.error("Check if user name '{}' already exist or trace log", newUser.username)
            log.error(e.message)
            throw InvalidUserException("Invalid user details")
        }
    }

    override fun updateUser(user: User) {
        userRepository.update(user)
    }

    override fun findByUsername(username: String): User {
        return userRepository.findByUsername(username)
    }

    override fun restore(id: String): User {
        var user = userRepository.findById(id)
        if (user == null) throw Exception("Invalid id ${id} in restore")
        var roles = userRoleRepository.findByUser(id)
        user.roles = roles
        return user
    }

    override fun findById(id: String): User{
        return userRepository.findById(id)
    }
}

package com.squer.promobee.controller

import com.squer.promobee.security.config.SecurityConstants
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.dto.LoginRequest
import com.squer.promobee.security.dto.LoginSuccessResponse
import com.squer.promobee.security.jwt.JwtTokenProvider
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.UserService
import com.squer.promobee.validator.MapValidationErrorService
import com.squer.promobee.validator.UserValidator
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Slf4j
open class UserController @Autowired constructor(
    private val userService: UserService,
    private val userValidator: UserValidator,
    private val authenticationManager: AuthenticationManager,
    private val jwdTokenProvider: JwtTokenProvider,
    private val mapValidationErrorService: MapValidationErrorService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var securityUtility: SecurityUtility

    @PostMapping("/login")
    fun authenticateUser(
        @RequestBody loginRequest: @Valid LoginRequest,
        result: BindingResult
    ): ResponseEntity<*> {
        println(bCryptPasswordEncoder.encode("welcome"))
        val errorMap = mapValidationErrorService.mapValidationService(result)
        if (errorMap != null) return errorMap
        val authentication: Authentication = authenticationManager.authenticate(

                UsernamePasswordAuthenticationToken(
                        loginRequest.username,
                        loginRequest.password
                )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = SecurityConstants.TOKEN_PREFIX + jwdTokenProvider.generateToken(authentication)
        log.info("User '${loginRequest.username}' is logged in and authenticated")
        return ResponseEntity.ok<Any>(LoginSuccessResponse(true, jwt))
    }


    @PutMapping(value = ["/setpassword"])
    open fun setPassword(@RequestBody loginRequest: LoginRequest): ResponseEntity<*>? {
        var user = userService.findByUsername(loginRequest.username)
        user!!.password = loginRequest.password
        userService.saveUser(user)
        return ResponseEntity<Any?>(user, HttpStatus.OK)
    }


    private fun returnInvalidResponseEntity(): ResponseEntity<Map<String, String>> {
        val errorMap: MutableMap<String, String> = HashMap()
        errorMap["message"] = "Invalid user details"
        errorMap["error"] = "true"
        return ResponseEntity(errorMap, HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/profile")
    fun getUserDetail(): ResponseEntity<*>?{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        return ResponseEntity(user, HttpStatus.OK)
    }

}

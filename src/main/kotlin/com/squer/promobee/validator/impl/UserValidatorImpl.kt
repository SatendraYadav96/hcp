package com.squer.promobee.validator.impl

import com.squer.promobee.security.domain.User
import com.squer.promobee.service.UserService
import com.squer.promobee.validator.UserValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors

@Component
class UserValidatorImpl @Autowired constructor(
    private val userService: UserService,
) : UserValidator {
    override fun supports(aClass: Class<*>): Boolean {
        return User::class.java == aClass
    }

    override fun validate(`object`: Any, errors: Errors) {
        val user: User = `object` as User
        if (user.username == null) {
            errors.rejectValue("userName", "NotNull", "UserName cannot be nu,,")
        } else if (userService.findByUsername(user.username!!) != null) {
            //verify phone number already exist
            errors.rejectValue(
                "userName",
                "Exist",
                "Username '${user.username}' already exist!"
            )
        }

        //clean user data
        user.name = user.name?.trim()
    }
}

package com.squer.promobee.controller

import com.squer.promobee.service.repository.domain.UserDesignation
import com.squer.promobee.service.UserDesignationService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Slf4j
open class UserDesignationController @Autowired constructor(
    private val userDesignationService: UserDesignationService
){

    @GetMapping("/getUserDesignation/{id}")
    fun getUserDesignation(@PathVariable id : String): UserDesignation {
        return userDesignationService.findById(id)
    }
}
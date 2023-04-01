package com.squer.promobee.controller

import com.squer.promobee.service.repository.domain.UserStatus
import com.squer.promobee.service.UserStatusService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping

@Slf4j
open class UserStatusController @Autowired constructor(
    private val userStatusService: UserStatusService
){

    @GetMapping("/getStatusList")
    fun getStatusList(): List<UserStatus>{
        return userStatusService.getStatusList()
    }
}
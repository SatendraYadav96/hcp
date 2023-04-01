package com.squer.promobee.service.impl

import com.squer.promobee.service.repository.domain.UserStatus
import com.squer.promobee.service.repository.UserStatusRepository
import com.squer.promobee.service.UserStatusService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class UserStatusServiceImpl @Autowired constructor(
    private val userStatusRepository: UserStatusRepository
): UserStatusService {

    override fun getStatusList(): List<UserStatus>{
         return userStatusRepository.getStatusList()
    }
}
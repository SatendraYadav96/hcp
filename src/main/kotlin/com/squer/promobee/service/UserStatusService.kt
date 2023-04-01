package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.UserStatus

interface UserStatusService {

    fun getStatusList() : List<UserStatus>
}
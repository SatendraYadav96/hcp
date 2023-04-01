package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.SystemLov

interface SystemLovService {

    fun getSystemLov(type: String): List<SystemLov>
}
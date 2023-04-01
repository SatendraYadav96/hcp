package com.squer.promobee.service.impl

import com.squer.promobee.service.SystemLovService
import com.squer.promobee.service.repository.SystemLovRepository
import com.squer.promobee.service.repository.domain.SystemLov
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class SystemLovServiceImpl @Autowired constructor(
        private val systemLovRepository: SystemLovRepository
): SystemLovService {

    override fun getSystemLov(type: String): List<SystemLov>{
        return systemLovRepository.getSystemLov(type)
    }
}
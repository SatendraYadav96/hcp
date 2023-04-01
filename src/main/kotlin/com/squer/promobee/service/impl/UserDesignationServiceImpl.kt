package com.squer.promobee.service.impl

import com.squer.promobee.service.repository.domain.UserDesignation
import com.squer.promobee.service.repository.UserDesignationRepository
import com.squer.promobee.service.UserDesignationService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class UserDesignationServiceImpl @Autowired constructor(
    private val userDesignationRepository: UserDesignationRepository)
    : UserDesignationService {

         override fun findById(id:String): UserDesignation {
            return userDesignationRepository.findById(id)
        }
}
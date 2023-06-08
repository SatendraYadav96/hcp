package com.squer.promobee.service.impl


import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.ComplianceService
import com.squer.promobee.service.repository.ComplianceRepository

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class ComplianceServiceImpl @Autowired constructor(
    private val complianceRepository: ComplianceRepository


): ComplianceService {


    private val log = LoggerFactory.getLogger(javaClass)

    override fun recipientUnblockingPartial(statusType: String, month: String, year: String): List<RecipientUnblockingPartialDTO> {
        return complianceRepository.recipientUnblockingPartial(statusType,month,year)
    }



}
package com.squer.promobee.service.impl



import com.squer.promobee.controller.dto.OptimaDataLogsDTO
import com.squer.promobee.controller.dto.OverSamplingDetaislDTO
import com.squer.promobee.controller.dto.RecipientBlockLogsDTO
import com.squer.promobee.controller.dto.RecipientUnblockingPartialDTO
import com.squer.promobee.service.ComplianceService
import com.squer.promobee.service.repository.ComplianceRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
@Slf4j
class ComplianceServiceImpl @Autowired constructor(
    private val complianceRepository: ComplianceRepository


): ComplianceService {


    private val log = LoggerFactory.getLogger(javaClass)

    override fun recipientUnblockingPartial(statusType: String, month: String, year: String): List<RecipientUnblockingPartialDTO> {
        return complianceRepository.recipientUnblockingPartial(statusType,month,year)
    }

    override fun optimaMailLogs(type: String, month: String, year: String): List<OptimaDataLogsDTO> {
        return complianceRepository.optimaMailLogs(type,month,year)
    }

    override fun overSamplingDetails(month: String, year: String): List<OverSamplingDetaislDTO> {
        return complianceRepository.overSamplingDetails(month,year)
    }

    override fun masterBlockedList(year: String): List<RecipientBlockLogsDTO> {
        return complianceRepository.masterBlockedList(year)
    }



}
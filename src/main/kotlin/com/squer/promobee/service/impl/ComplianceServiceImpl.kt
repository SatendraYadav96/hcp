package com.squer.promobee.service.impl



import com.squer.promobee.controller.dto.*
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

    override fun overSamplingDetails(month: String, year: String): List<ComplianceListCrudDTO> {
        return complianceRepository.overSamplingDetails(month,year)
    }

    override fun masterBlockedList(year: String): List<RecipientBlockedListCrudDTO> {
        return complianceRepository.masterBlockedList(year)
    }

    override fun saveMasterBlockedRecipient(blockRecp: List<SaveRecipientBlockedmasterDTO>) {
        return complianceRepository.saveMasterBlockedRecipient(blockRecp)
    }

    override fun saveOverSampling(comp: List<SaveOverSamplingDTO>) {
        return complianceRepository.saveOverSampling(comp)
    }

    override fun saveNonComplianceAdminRemark(nonComp: List<SaveNonComplianceAdminRemarkDTO>) {
        return complianceRepository.saveNonComplianceAdminRemark(nonComp)
    }

}
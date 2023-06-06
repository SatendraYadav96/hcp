package com.squer.promobee.service.impl


import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.controller.dto.MontlyApprovalBexDTO
import com.squer.promobee.service.ApprovalService
import com.squer.promobee.service.repository.ApprovalRepository
import com.squer.promobee.service.repository.domain.DispatchPlan
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class ApprovalServiceImpl @Autowired constructor(
    private val approvalRepository: ApprovalRepository


): ApprovalService {


    private val log = LoggerFactory.getLogger(javaClass)

    override fun getMonthlyApprovalForBex(month: Int, year: Int, userId: String, userDesgId: String): List<MontlyApprovalBexDTO> {
        return approvalRepository.getMonthlyApprovalForBex(month,year,userId,userDesgId)
    }

    override fun getDispatchPlanById(id: String): DispatchPlan {
        return approvalRepository.getDispatchPlanById(id)
    }

    override fun getMonthlyApprovalDetails(
        userId: String,
        planId: String
    ): List<AllocationInventoryDetailsWithCostCenterDTO> {
        return approvalRepository.getMonthlyApprovalDetails(userId,planId)
    }


}
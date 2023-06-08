package com.squer.promobee.service.impl


import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.ApprovalService
import com.squer.promobee.service.repository.ApprovalRepository
import com.squer.promobee.service.repository.domain.ApprovalChainTransaction
import com.squer.promobee.service.repository.domain.DispatchDetail
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


    // MONTHLY APPROVAL

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

    override fun unlockPlanForUserByMonthAndYear(plan: UnlockPlanDto) {
        return approvalRepository.unlockPlanForUserByMonthAndYear(plan)
    }

    override fun resetDraftPlan(planId: String) {
        return approvalRepository.resetDraftPlan(planId)
    }

    override fun getApprovalChainById(id: String): ApprovalChainTransaction {
        return approvalRepository.getApprovalChainById(id)
    }

    override fun getApprovalChainForSpecialPlanConvert(id: String): ApprovalChainTransaction {
        return approvalRepository.getApprovalChainForSpecialPlanConvert(id)
    }

    override fun approvePlan(plan: ApproveRejectPlanDto) {
        return approvalRepository.approvePlan(plan)
    }

    override fun getDispatchDetails(planId: String): List<DispatchDetail> {
        return approvalRepository.getDispatchDetails(planId)
    }

    override fun getDispatchPlanCount(id: String): DispatchPlan {
        return approvalRepository.getDispatchPlanCount(id)
    }

    override fun rejectPlan(plan: ApproveRejectPlanDto) {
        return approvalRepository.rejectPlan(plan)
    }

    override fun saveMonthlyToSpecial(plan: SaveMonthlyToSpecialDTO) {
        return approvalRepository.saveMonthlyToSpecial(plan)
    }


    // SPECIAL APPROVAL

    override fun getSpecialPlanForApproval(month: Int, year: Int, userId: String, userDesgId: String): List<MontlyApprovalBexDTO> {
        return approvalRepository.getSpecialPlanForApproval(month,year,userId,userDesgId)
    }

    override fun getSpecialPlanApprovalDetails(planId: String): List<SpecialAllocationDetailsForApprovalDTO> {
        return approvalRepository.getSpecialPlanApprovalDetails(planId)
    }

    // VIRTUAL APPROVAL

    override fun getVirtualPlanForApproval(month: Int, year: Int, userId: String, userDesgId: String): List<MontlyApprovalBexDTO> {
        return approvalRepository.getVirtualPlanForApproval(month,year,userId,userDesgId)
    }

    override fun getVirtualPlanApprovalDetails(planId: String, teamId: String): List<SpecialAllocationDetailsForApprovalDTO> {
        return approvalRepository.getVirtualPlanApprovalDetails(planId,teamId)
    }

}
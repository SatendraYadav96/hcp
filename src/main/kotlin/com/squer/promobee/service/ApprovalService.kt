package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.ApprovalChainTransaction
import com.squer.promobee.service.repository.domain.DispatchDetail
import com.squer.promobee.service.repository.domain.DispatchPlan


interface ApprovalService {

    fun getMonthlyApprovalForBex(month : Int,  year : Int,  userId : String,  userDesgId : String) : List<MontlyApprovalBexDTO>

    fun getDispatchPlanById(id : String) : DispatchPlan

    fun getMonthlyApprovalDetails( userId : String,  planId : String) : List<AllocationInventoryDetailsWithCostCenterDTO>


    fun unlockPlanForUserByMonthAndYear(plan : UnlockPlanDto)

    fun resetDraftPlan(planId : String)

    fun getApprovalChainById(id : String): ApprovalChainTransaction

    fun getApprovalChainForSpecialPlanConvert(id : String, desgId : String): ApprovalChainTransaction


    fun approvePlan(plan : ApproveRejectPlanDto)

    fun getDispatchDetails(planId : String): List<DispatchDetail>

    fun getDispatchPlanCount(id : String): DispatchPlan

    fun rejectPlan(plan : ApproveRejectPlanDto)

    fun saveMonthlyToSpecial(plan : SaveMonthlyToSpecialDTO)




}
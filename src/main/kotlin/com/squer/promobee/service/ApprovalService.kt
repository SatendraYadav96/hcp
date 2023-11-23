package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.ApprovalChainTransaction
import com.squer.promobee.service.repository.domain.DispatchDetail
import com.squer.promobee.service.repository.domain.DispatchPlan


interface ApprovalService {


    // MONTHLY APPROVAL

    fun getMonthlyApprovalForBex(month : Int,  year : Int,  userId : String,  userDesgId : String) : List<MontlyApprovalBexDTO>

    fun getDispatchPlanById(id : String) : DispatchPlan

    fun getMonthlyApprovalDetails( userId : String,  planId : String) : List<AllocationInventoryDetailsWithCostCenterDTO>


    fun unlockPlanForUserByMonthAndYear(plan : UnlockPlanDto)

    fun resetDraftPlan(planId : String)

    fun getApprovalChainById(id : String): ApprovalChainTransaction

    fun getApprovalChainForSpecialPlanConvert(id : String): ApprovalChainTransaction


    fun approvePlan(plan : ApproveRejectPlanDto)

    fun getDispatchDetails(planId : String): List<DispatchDetail>

    fun getDispatchPlanCount(id : String): DispatchPlan

    fun rejectPlan(plan : ApproveRejectPlanDto)

    fun saveMonthlyToSpecial(plan : SaveMonthlyToSpecialDTO)


    // SPECIAL APPROVAL

    fun getSpecialPlanForApproval(month : Int,  year : Int,  userId : String,  userDesgId : String) : List<MontlyApprovalBexDTO>

    fun getSpecialPlanApprovalDetails(planId : String) : List<SpecialAllocationDetailsForApprovalDTO>


    // VIRTUAL APPROVAL

    fun getVirtualPlanForApproval(month : Int,  year : Int,  userId : String,  userDesgId : String) : List<MontlyApprovalBexDTO>

    fun getVirtualPlanApprovalDetails(planId : String) : List<SpecialAllocationDetailsForApprovalDTO>

    fun virtualAllocationDownload(vrl : List<VirtualAllocationDownloadDTO>) :List<VirtualAllocationDetailsDTO>





}
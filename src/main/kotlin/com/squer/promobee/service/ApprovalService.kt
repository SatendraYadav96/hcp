package com.squer.promobee.service

import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.controller.dto.MontlyApprovalBexDTO
import com.squer.promobee.controller.dto.RecipientReportDTO
import com.squer.promobee.service.repository.domain.DispatchPlan


interface ApprovalService {

    fun getMonthlyApprovalForBex(month : Int,  year : Int,  userId : String,  userDesgId : String) : List<MontlyApprovalBexDTO>

    fun getDispatchPlanById(id : String) : DispatchPlan

    fun getMonthlyApprovalDetails( userId : String,  planId : String) : List<AllocationInventoryDetailsWithCostCenterDTO>



}
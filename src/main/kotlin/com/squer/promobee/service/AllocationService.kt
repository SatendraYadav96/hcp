package com.squer.promobee.service

import com.squer.promobee.api.v1.enums.AllocationStatusEnum
import com.squer.promobee.api.v1.enums.DispatchPlanInvoiceStatus
import com.squer.promobee.api.v1.enums.MonthlyPlanStatusEnum
import com.squer.promobee.controller.dto.AllocationDetailsDTO
import com.squer.promobee.controller.dto.AllocationDistributionDTO
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.repository.domain.DispatchPlan
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
@Slf4j
class AllocationService @Autowired constructor(
        private val dispatchPlanService: DispatchPlanService,
        private val inventoryService: InventoryService,
        private val systemLovService: SystemLovService,
        private val teamService: TeamService,
) {

    fun createViewMonthlyPlan(year:Int,month:Int): AllocationDetailsDTO {
//        val month = (yearMonth % 100).toInt()
//        val year = (yearMonth / 100).toInt()
        var allocationDetailsDTO = AllocationDetailsDTO()
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var plan = dispatchPlanService.getPlanHeaderSelect(month, year, user.id)
        if(plan == null){
            var dispatchplan = DispatchPlan()
            dispatchplan.id = UUID.randomUUID().toString()
            dispatchplan.owner = NamedSquerEntity(user.id, "")
            dispatchplan.month = month
            dispatchplan.year = year
            dispatchplan.planStatus = NamedSquerEntity(MonthlyPlanStatusEnum.DRAFT_ID.id, "")
            dispatchplan.isSpecial = 0
            dispatchplan.remarks = null
            dispatchplan.invoiceStatus = NamedSquerEntity(DispatchPlanInvoiceStatus.NOT_INITIATED.id, "")
            dispatchplan.quarterlyPlan =  null
            dispatchplan.isVirtual = 0
            dispatchplan.createdBy = user.id
            dispatchplan.updatedBy = user.id
            dispatchPlanService.insertPlan(dispatchplan)
            plan = dispatchPlanService.getPlanHeaderSelect(month, year, user.id)
        }
        allocationDetailsDTO.plan = plan
        var items = inventoryService.getMonthlyAllocation(plan!!.id,user.id)

       var planSubmit = ""

        if(plan.planStatus!!.id == AllocationStatusEnum.SUBMIT.id || plan.planStatus!!.id == AllocationStatusEnum.APPROVED.id){
            println(" Allocation submitted Successfully !")
            planSubmit = "true"
        }else{
            println(" Allocation is in draft mode !")
            planSubmit = "false"
        }
        allocationDetailsDTO.item = items
        allocationDetailsDTO.planSubmitted = planSubmit

        return allocationDetailsDTO
    }

    fun allocationForPlan(planId:String):AllocationDistributionDTO{
        val data = AllocationDistributionDTO()
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        data.teams = teamService.getTeamsForUser(user.id)
        data.allocations = inventoryService.getInventoryDistributionByTeamForPlan(planId)
        return data
    }






}
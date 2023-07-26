package com.squer.promobee.service

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

    fun createViewMonthlyPlan(yearMonth: Long): AllocationDetailsDTO {
        val month = (yearMonth % 100).toInt()
        val year = (yearMonth / 100).toInt()
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
            dispatchplan.invoiceStatus = null
            dispatchplan.quarterlyPlan =  null
            dispatchplan.isVirtual = 0
            dispatchplan.createdBy = user.id
            dispatchplan.updatedBy = user.id
            dispatchPlanService.insertPlan(dispatchplan)
            plan = dispatchPlanService.getPlanHeaderSelect(month, year, user.id)
        }
        allocationDetailsDTO.plan = plan
        val items = inventoryService.getMonthlyAllocation(plan!!.id,user.id)
        allocationDetailsDTO.item = items
        //IsPlanApprovedOrSubmit
        /*val allocationTypes = systemLovService.getSystemLov(SLVTypeEnum.MONTHLY_PLAN_STATUS.toString())
        val plans = dispatchPlanService.getPlanHeaderSelect(month, year, user.id, 0 )
        var allocationStatus: String? = null
        allocationTypes.forEach{
            if(it.id == plans!!.planStatus!!.id){ allocationStatus = it.name}
        }*/
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
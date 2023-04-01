package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.DataModelInvoiceDetailsDTO
import com.squer.promobee.controller.dto.GroupingInvoiceDetailsDTO
import com.squer.promobee.controller.dto.PickingSlipDTO
import com.squer.promobee.controller.dto.TeamInvoiceDTO
import com.squer.promobee.service.DispatchPlanService
import com.squer.promobee.service.repository.DispatchPlanRepository
import com.squer.promobee.service.repository.domain.DispatchPlan
import com.squer.promobee.controller.dto.TeamPlanInvoiceDTO
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
@Slf4j
class DispatchPlanServiceImpl @Autowired constructor(
        private val dispatchPlanRepository:DispatchPlanRepository


): DispatchPlanService {

    override fun getPlanHeaderSelect(monthPlan: Int, yearPlan: Int, userId: String, isSpecial: Int?): DispatchPlan? {
        return dispatchPlanRepository.getPlanHeaderSelect(monthPlan, yearPlan, userId, isSpecial)
    }

    override fun insertPlan(plan: DispatchPlan) {
        return dispatchPlanRepository.insertPlan(plan)
    }

    override fun getPickingList(month: Int, year: Int, dispatchType: String): List<PickingSlipDTO> {
        return dispatchPlanRepository.getPickingList(month, year, dispatchType)
    }

    override fun getMonthlyDispatchSearch(month: Int, year: Int) : List<TeamInvoiceDTO>{
        return dispatchPlanRepository.getMonthlyDispatchSearch(month, year)
    }

    override fun getEmployeeInvoiceDetails(month: Int, year: Int, isSpecialDisp: String, teamId: String, status: String): List<DataModelInvoiceDetailsDTO>{
        return dispatchPlanRepository.getEmployeeInvoiceDetails(month, year, isSpecialDisp, teamId, status)
    }

    override fun getGroupingInvoiceForHUB(fromDate: Date, toDate: Date, invoiceNumber: Int): List<GroupingInvoiceDetailsDTO>{
        return dispatchPlanRepository.getGroupingInvoiceForHUB(fromDate, toDate, invoiceNumber)
    }

    override fun getInvoicesForGrouping(fromDate: Date, toDate: Date, invoiceNumber: Int): List<GroupingInvoiceDetailsDTO>{
        return dispatchPlanRepository.getInvoicesForGrouping(fromDate, toDate, invoiceNumber)
    }

    override fun getSpecialDispatchSearch(year: Int, month: Int): List<TeamPlanInvoiceDTO> {
        return dispatchPlanRepository.getSpecialDispatchSearch(year,month)
    }

    override fun getSpecialEmployeeInvoiceDetails(planId: String, status: String, id: String): List<DataModelInvoiceDetailsDTO> {
        return dispatchPlanRepository.getSpecialEmployeeInvoiceDetails(planId, status)
    }

    override fun getVirtualDispatchSearch(month: Int, year: Int) : List<TeamPlanInvoiceDTO>{
        return dispatchPlanRepository.getVirtualDispatchSearch(month, year)
    }

}
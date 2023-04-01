package com.squer.promobee.service

import com.squer.promobee.controller.dto.DataModelInvoiceDetailsDTO
import com.squer.promobee.controller.dto.GroupingInvoiceDetailsDTO
import com.squer.promobee.controller.dto.PickingSlipDTO
import com.squer.promobee.controller.dto.TeamInvoiceDTO
import com.squer.promobee.service.repository.domain.DispatchPlan
import com.squer.promobee.controller.dto.TeamPlanInvoiceDTO
import java.util.*

interface DispatchPlanService {

    fun getPlanHeaderSelect(monthPlan: Int, yearPlan: Int, userId: String, isSpecial: Int ?= null): DispatchPlan?
    fun insertPlan(plan: DispatchPlan)
    fun getPickingList(month: Int, year: Int, dispatchType: String): List<PickingSlipDTO>
    fun getMonthlyDispatchSearch(month: Int, year: Int) : List<TeamInvoiceDTO>
    fun getEmployeeInvoiceDetails(month: Int, year: Int, isSpecialDisp: String, teamId: String, status: String): List<DataModelInvoiceDetailsDTO>
    fun getGroupingInvoiceForHUB(fromDate: Date, toDate: Date, invoiceNumber: Int): List<GroupingInvoiceDetailsDTO>
    fun getInvoicesForGrouping(fromDate: Date, toDate: Date, invoiceNumber: Int): List<GroupingInvoiceDetailsDTO>
    fun getSpecialDispatchSearch(year: Int,month: Int) : List<TeamPlanInvoiceDTO>
    //fun getSpecialEmployeeInvoiceDetails(planId: String, status: String): List<DataModelInvoiceDetailsDTO>
    fun getSpecialEmployeeInvoiceDetails(planId: String, status: String,id:String): List<DataModelInvoiceDetailsDTO>
    fun getVirtualDispatchSearch(month: Int, year: Int) : List<TeamPlanInvoiceDTO>
}
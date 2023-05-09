package com.squer.promobee.service

import com.squer.promobee.api.v1.enums.AllocationStatusEnum
import com.squer.promobee.api.v1.enums.InvoiceStatusEnum
import com.squer.promobee.api.v1.enums.UserLovEnum
import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.repository.domain.Team
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Service
@Slf4j
class DispatchInvoicingService @Autowired constructor(
        private val dispatchPlanService: DispatchPlanService,
        private val teamLegalEntityService: TeamLegalEntityService,
        private val teamService: TeamService,
        private val transporterService: TransporterService
){

    fun getPickingList(year: Int, month: Int, dispatchType: String): MutableList<PickListDetailsDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var pickListDetails : MutableList<PickListDetailsDTO> = mutableListOf()
        var GetpickListDetails : List<PickingSlipDTO>
        GetpickListDetails = dispatchPlanService.getPickingList(month, year, dispatchType)

        val userEty = user.legalEntity
        val userLegalTeam = teamLegalEntityService.getUsersLegalTeam(userEty!!.id)
        var userTeams : List<Team>

        if(user.userDesignation!!.id === UserLovEnum.PRODUCT_MANAGER_DESIGNATION.id){
            userTeams = teamService.getBMTeams(user.id)
        }else if(user.userDesignation!!.id == UserLovEnum.HUB_MANAGER_DESIGNATION.id)
        {
            userTeams = teamService.getTeam()
        }else{
            var team = userLegalTeam.map{it.team!!.id}
            println(team)
            userTeams = teamService.getTeam(team)
        }

        var teamId = userTeams.map { it.id }
        if(dispatchType != "Virtual" && dispatchType != "1") {
            var list : MutableList<PickingSlipDTO> = mutableListOf()
            GetpickListDetails.forEach{
                if(teamId.contains(it.teamID)){
                    list.add(it)
                }
            }
            GetpickListDetails = list
        }

        if(GetpickListDetails !== null){
            GetpickListDetails.map{
                var pickList = PickListDetailsDTO()
                pickList.team= it.team
                pickList.teamID = it.teamID
                pickList.ownerId = it.ownerId
                pickList.ownerName = it.brandManager
                pickList.months = it.months
                pickList.years = it.years
                pickList.planName = it.planName
                pickList.planID = it.planID
                pickList.planInvoiceStatus = it.planInvoiceStatus
                pickList.approvalDate = it.approvalDate
                pickListDetails.add(pickList)
            }
        }
        return pickListDetails
    }

    fun getMonthlyDispatchSearch(month: Int, year: Int): List<TeamInvoiceDTO>{
        return dispatchPlanService.getMonthlyDispatchSearch(month, year)
    }

    fun getEmployeeInvoiceDetails(month: Int, year: Int, isSpecialDisp: String, teamId: String, status: String): MutableList<EmployeeInvoiceDetailsDTO> {
        var transporterDetails = transporterService.getTransporter()
        var transporterSelectList= mutableMapOf<String, String>()
        transporterDetails.forEach{
            transporterSelectList[it.id] = it.name.toString()
        }

        var invoiceDetails : List<DataModelInvoiceDetailsDTO> = mutableListOf()
        if(status == InvoiceStatusEnum.DRAFT.id){
            invoiceDetails = dispatchPlanService.getEmployeeInvoiceDetails(month, year, isSpecialDisp, teamId, AllocationStatusEnum.APPROVED.id)
        }else if(status == InvoiceStatusEnum.GENERATED_PRINTED.id){
            invoiceDetails = dispatchPlanService.getEmployeeInvoiceDetails(month, year, isSpecialDisp, teamId, InvoiceStatusEnum.GENERATED_PRINTED.id)
        }else if(status == InvoiceStatusEnum.CANCELLED.id){
            invoiceDetails = dispatchPlanService.getEmployeeInvoiceDetails(month, year, isSpecialDisp, teamId, InvoiceStatusEnum.CANCELLED.id)
        }else if(status == InvoiceStatusEnum.REDIRECTED.id){
            invoiceDetails = dispatchPlanService.getEmployeeInvoiceDetails(month, year, isSpecialDisp, teamId, InvoiceStatusEnum.REDIRECTED.id)
        }

        var employeeInvoiceDetails : MutableList<EmployeeInvoiceDetailsDTO> = mutableListOf()
        invoiceDetails.forEach{
            var e = EmployeeInvoiceDetailsDTO()
            e.invoiceHeaderID = it.invoiceHeaderID
            e.employeeName = it.employeeName
            e.employeeID = it.employeeId
            e.code = it.code
            e.invoiceNumber = it.invoiceNumber
            e.groupInvoiceNumber = it.groupInvoiceNumber
            e.invoiceStatus = it.invoiceStatus
            e.invoiceStatusID = it.invoiceStatusID
            e.boxes = it.boxes
            e.weight = it.weight
            e.state = it.state
            e.city = it.city
            e.teamName = it.teamName
            e.transporterDetails = it.transporterID
            e.lrNumber = it.lrNumber
            e.redirectedFrom = it.redirectedFrom
            e.redirectedTo = it.redirectedTo
            employeeInvoiceDetails.add(e)
        }

        return employeeInvoiceDetails
    }

    fun getGroupingInvoiceForHUB(fromDate: Date, toDate: Date, invoiceNumber: Int, type: String): List<GroupingInvoiceDetailsDTO>{
       if(type == "search"){
           return dispatchPlanService.getGroupingInvoiceForHUB(fromDate, toDate, invoiceNumber)
       }else{
            return dispatchPlanService.getInvoicesForGrouping(fromDate, toDate, invoiceNumber)
       }
    }


    fun getSpecialDispatchSearch (year: Int, month: Int): List<TeamPlanInvoiceDTO>{
        return dispatchPlanService.getSpecialDispatchSearch(year,month)
    }


    fun getSpecialEmployeeInvoiceDetails(planId: String, status: String): MutableList<EmployeeInvoiceDetailsDTO> {
        var transporterDetails = transporterService.getTransporter()
        var transporterSelectList= mutableMapOf<String, String>()
        transporterDetails.forEach{
            transporterSelectList[it.id] = it.name.toString()
        }

        var invoiceDetails : List<DataModelInvoiceDetailsDTO> = mutableListOf()
        if(status == InvoiceStatusEnum.DRAFT.id){
            invoiceDetails = dispatchPlanService.getSpecialEmployeeInvoiceDetails(planId,status, AllocationStatusEnum.APPROVED.id)
        }else if(status == InvoiceStatusEnum.GENERATED_PRINTED.id){
            invoiceDetails = dispatchPlanService.getSpecialEmployeeInvoiceDetails(planId,status, InvoiceStatusEnum.GENERATED_PRINTED.id)
        }else if(status == InvoiceStatusEnum.CANCELLED.id){
            invoiceDetails = dispatchPlanService.getSpecialEmployeeInvoiceDetails(planId,status, InvoiceStatusEnum.CANCELLED.id)
        }else if(status == InvoiceStatusEnum.REDIRECTED.id){
            invoiceDetails = dispatchPlanService.getSpecialEmployeeInvoiceDetails(planId,status, InvoiceStatusEnum.REDIRECTED.id)
        }

        var employeeInvoiceDetails : MutableList<EmployeeInvoiceDetailsDTO> = mutableListOf()
        invoiceDetails.forEach{
            var e = EmployeeInvoiceDetailsDTO()
            e.invoiceHeaderID = it.invoiceHeaderID
            e.employeeName = it.employeeName
            e.employeeID = it.employeeId
            e.code = it.code
            e.invoiceNumber = it.invoiceNumber
            e.groupInvoiceNumber = it.groupInvoiceNumber
            e.invoiceStatus = it.invoiceStatus
            e.invoiceStatusID = it.invoiceStatusID
            e.boxes = it.boxes
            e.weight = it.weight
            e.state = it.state
            e.city = it.city
            e.teamName = it.teamName
            e.transporterDetails = it.transporterID
            e.lrNumber = it.lrNumber
            e.redirectedFrom = it.redirectedFrom
            e.redirectedTo = it.redirectedTo
            e.isVirtual = 0
            employeeInvoiceDetails.add(e)
        }

        return employeeInvoiceDetails
    }


    fun getVirtualDispatchSearch(month: Int, year: Int): List<TeamPlanInvoiceDTO>{
        return dispatchPlanService.getVirtualDispatchSearch(month, year)
    }






}
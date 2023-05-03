package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import java.util.*


interface ReportService {

//    fun getReportRecipient(businessUnit: String, divison: String,team:String,statusId:String) : List<RecipientReportDTO>

    fun getReportRecipient(businessUnit: String, team:String,statusId:String) : List<RecipientReportDTO>

    fun getReportPurchase(startDate: String, endDate: String, userId:String, userDesgId:String, businessUnit: String, divison: String): List<PurchaseReportDTO>

    fun getReportDispatches(startDate: String,endDate: String,filter:String,filterPlan:String,userId:String,userDesgId:String,businessUnit: String, divison: String): List<DispatchesReportDTO>

    fun getReportDispatchRegister(startDate: String,endDate: String,userId:String,userDesgId:String, businessUnit: String,team:String,filterPlan:Int): List<DispatchRegisterReportDTO>

    fun getReportDeviation(quarterName:String,fromDate: String,toDate: String,userId:String,userDesgId:String): List<DeviationReportDTO>

    fun getReportItemConsumption(fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String): List<ItemConsumptionReportDTO>

    fun getReportDestruction(fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String,statusId: String): List<DestructionReportDTO>

    fun getReportSimpleInventory(businessUnit: String, divison: String,userId:String, userDesgId:String): List<SimpleInventoryReportDTO>

    fun getReportNearToExpirySample(businessUnit: String, divison: String,userId:String, userDesgId:String,type:String): List<NearToExpiryReportDTO>

    fun getReportNearToExpiryInput(businessUnit: String, divison: String,userId:String, userDesgId:String,type:String): List<NearToExpiryReportDTO>

    fun getReportSpecialDispatch(fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String): List<SpecialDispatchReportDTO>

    fun getReportDispatchByTeam(year: String, special: String): List<DispatchByTeamReportDTO>

}
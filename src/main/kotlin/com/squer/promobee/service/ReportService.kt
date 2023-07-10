package com.squer.promobee.service

import com.squer.promobee.controller.dto.*


interface ReportService {

//    fun getReportRecipient(businessUnit: String, divison: String,team:String,statusId:String) : List<RecipientReportDTO>

    fun getReportRecipient(ff: FFReportDTO) : List<RecipientReportDTO>

    fun getReportPurchase(startDate: String, endDate: String, userId:String, userDesgId:String, businessUnit: String, divison: String): List<PurchaseReportDTO>

    fun getReportDispatches(startDate: String,endDate: String,filter:Int,filterPlan:Int,userId:String,userDesgId:String,businessUnit: String, division: String): List<DispatchesReportDTO>

    fun getReportDispatchRegister(startDate: String,endDate: String,userId:String,userDesgId:String, businessUnit: String,division: String,statusId: String, team:String,filterPlan:Int): List<DispatchRegisterReportDTO>

    fun getReportDeviation(quarterName:String,fromDate: String,toDate: String,userId:String,userDesgId:String): List<DeviationReportDTO>

    fun getReportItemConsumption(fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String): List<ItemConsumptionReportDTO>

    fun getReportDestruction(fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String,statusId: String): List<DestructionReportDTO>

    fun getReportSimpleInventory(simInv: SimpleInvenotryReportDTO): List<SimpleInventoryReportDTO>

    fun getReportNearToExpirySample(businessUnit: String, divison: String,userId:String, userDesgId:String,type:String): List<NearToExpiryReportDTO>

    fun getReportNearToExpiryInput(businessUnit: String, divison: String,userId:String, userDesgId:String,type:String): List<NearToExpiryReportDTO>

    fun getReportSpecialDispatch(fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String): List<SpecialDispatchReportDTO>

    fun getReportDispatchByTeam(year: String, special: String): List<DispatchByTeamReportDTO>

    fun getItemWiseReport(fromDate: String,toDate: String,businessUnit: String,divison: String): List<ItemWiseReportDTO>

    fun getStockLedgerReport(fromDate: String,toDate: String,itemId: String): List<StockLedgerReportDTO>

    fun getAgeingReport(userId: String,userDesgId: String,businessUnit: String,divison: String): List<AgeingReportDTO>

    fun getShiprocketReport(fromDate: String,toDate: String): List<ShiprocketReportDTO>

    fun getVirtualReconciliationReport(fromDate: String,toDate: String,businessUnit: String): List<VirtualReconciliationDTO>
}
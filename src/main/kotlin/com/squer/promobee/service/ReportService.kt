package com.squer.promobee.service

import com.squer.promobee.controller.dto.*


interface ReportService {

//    fun getReportRecipient(businessUnit: String, divison: String,team:String,statusId:String) : List<RecipientReportDTO>

    fun getReportRecipient(ff: FFReportDTO) : List<RecipientReportDTO>

    fun getReportPurchase(pur : PurchaseReportParamDTO): List<PurchaseReportDTO>

    fun getReportDispatches(disp : DispatchesReportParamDto): List<DispatchesReportDTO>

    fun getReportDispatchRegister(dispReg : DispatchRegisterParamDTO): List<DispatchRegisterReportDTO>

    fun getReportDeviation(quarterName:String,fromDate: String,toDate: String,userId:String,userDesgId:String): List<DeviationReportDTO>

    fun getReportItemConsumption(item : ItemConsumptionParamDTO): List<ItemConsumptionReportDTO>

    fun getReportDestruction(dest: DestructionReportParamDTO): List<DestructionReportDTO>

    fun getReportSimpleInventory(simInv: SimpleInvenotryParamDTO): List<SimpleInventoryReportDTO>

    fun getReportNearToExpirySample(sample: NearExpiryReportParamDTO): List<NearToExpiryReportDTO>

    fun getReportNearToExpiryInput(input: NearExpiryReportParamDTO): List<NearToExpiryReportDTO>

    fun getReportSpecialDispatch(speDisp:SpecialDispatchReportParamDTO): List<SpecialDispatchReportDTO>

    fun getReportDispatchByTeam(year: String, special: String): List<DispatchByTeamReportDTO>

    fun getItemWiseReport(item : ItemWiseReportParamDTO): List<ItemWiseReportDTO>

    fun getStockLedgerReport(fromDate: String,toDate: String,itemId: String): List<StockLedgerReportDTO>

    fun getAgeingReport(age:AgeingReportParamDTO): List<AgeingReportDTO>

    fun getShiprocketReport(fromDate: String,toDate: String): List<ShiprocketReportDTO>

    fun getVirtualReconciliationReport(quarter: String,year: String,businessUnit: ArrayList<String> ): List<VirtualReconciliationDTO>

    fun getBatchReconciliation(): List<BatchReconciliationDTO>
}
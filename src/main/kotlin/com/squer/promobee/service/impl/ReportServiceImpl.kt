package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.ReportService
import com.squer.promobee.service.repository.ReportRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
@Slf4j
class ReportServiceImpl @Autowired constructor(
    private val reportRepository: ReportRepository


): ReportService {

//    override fun getReportRecipient(businessUnit: String, divison: String,team:String,statusId:String) : List<RecipientReportDTO>{
//        return reportRepository.getReportRecipient(businessUnit, divison, team , statusId)
//    }

    override fun getReportRecipient(ff: FFReportDTO) : List<RecipientReportDTO>{
        return reportRepository.getReportRecipient(ff)
    }


    override fun getReportPurchase(startDate: String, endDate: String, userId:String, userDesgId:String, businessUnit: String, divison: String): List<PurchaseReportDTO>{
        return reportRepository.getReportPurchase(startDate,endDate,userId,userDesgId,businessUnit,divison)
    }

    override fun getReportDispatches(startDate: String,endDate: String,filter:Int,filterPlan:Int,userId:String,userDesgId:String,businessUnit: String, division: String): List<DispatchesReportDTO>{
        return reportRepository.getReportDispatches(startDate,endDate,filter,filterPlan,userId,userDesgId,businessUnit,division)
    }

    override fun getReportDispatchRegister(startDate: String,endDate: String,userId:String,userDesgId:String, businessUnit: String,division: String ,team:String,statusId: String ,filterPlan:Int): List<DispatchRegisterReportDTO>{
        return reportRepository.getReportDispatchRegister(startDate,endDate,userId,userDesgId,businessUnit,division,team,statusId,filterPlan)
    }

    override fun getReportDeviation(quarterName:String,fromDate: String,toDate: String,userId:String,userDesgId:String): List<DeviationReportDTO>{
        return reportRepository.getReportDeviation(quarterName,fromDate,toDate,userId,userDesgId)
    }

    override fun getReportItemConsumption(fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String): List<ItemConsumptionReportDTO>{
        return reportRepository.getReportItemConsumption(fromDate,toDate,userId,userDesgId,businessUnit,divison)
    }

    override fun getReportDestruction(fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String,statusId: String): List<DestructionReportDTO>{
        return reportRepository.getReportDestruction(fromDate,toDate,userId,userDesgId,businessUnit,divison,statusId)
    }


    override fun getReportSimpleInventory(simInv: SimpleInvenotryParamDTO): List<SimpleInventoryReportDTO>{
        return reportRepository.getReportSimpleInventory(simInv)
    }

    override fun getReportNearToExpirySample(businessUnit: String, divison: String,userId:String, userDesgId:String,type:String): List<NearToExpiryReportDTO>{
        return reportRepository.getReportNearToExpirySample(businessUnit,divison,userId,userDesgId,type)
    }

    override fun getReportNearToExpiryInput(businessUnit: String, divison: String,userId:String, userDesgId:String,type:String): List<NearToExpiryReportDTO>{
        return reportRepository.getReportNearToExpiryInput(businessUnit,divison,userId,userDesgId,type)
    }

    override fun getReportSpecialDispatch(fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String): List<SpecialDispatchReportDTO>{
        return reportRepository.getReportSpecialDispatch(fromDate,toDate,userId,userDesgId,businessUnit,divison)
    }


    override fun getReportDispatchByTeam(year: String,special: String): List<DispatchByTeamReportDTO>{
        return reportRepository.getReportDispatchByTeam(year,special)
    }

    override fun getItemWiseReport(fromDate: String,toDate: String,businessUnit: String,divison: String): List<ItemWiseReportDTO>{
        return reportRepository.getItemWiseReport(fromDate,toDate,businessUnit,divison)
    }

    override fun getStockLedgerReport(fromDate: String,toDate: String,itemId: String): List<StockLedgerReportDTO>{
        return reportRepository.getStockLedgerReport(fromDate,toDate,itemId)
    }

    override fun getAgeingReport(userId:String,userDesgId:String,businessUnit: String,divison: String): List<AgeingReportDTO>{
        return reportRepository.getAgeingReport(userId,userDesgId,businessUnit,divison)
    }

    override fun getShiprocketReport(fromDate: String,toDate: String): List<ShiprocketReportDTO>{
        return reportRepository.getShiprocketReport(fromDate,toDate)
    }

    override fun getVirtualReconciliationReport(fromDate: String, toDate: String, businessUnit: String): List<VirtualReconciliationDTO> {
        return reportRepository.getVirtualReconciliationReport(fromDate,toDate,businessUnit)
    }

}
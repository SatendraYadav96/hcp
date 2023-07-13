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


    override fun getReportPurchase(pur : PurchaseReportParamDTO): List<PurchaseReportDTO>{
        return reportRepository.getReportPurchase(pur)
    }

    override fun getReportDispatches(disp : DispatchesReportParamDto): List<DispatchesReportDTO>{
        return reportRepository.getReportDispatches(disp)
    }

    override fun getReportDispatchRegister(dispReg : DispatchRegisterParamDTO): List<DispatchRegisterReportDTO>{
        return reportRepository.getReportDispatchRegister(dispReg)
    }

    override fun getReportDeviation(quarterName:String,fromDate: String,toDate: String,userId:String,userDesgId:String): List<DeviationReportDTO>{
        return reportRepository.getReportDeviation(quarterName,fromDate,toDate,userId,userDesgId)
    }

    override fun getReportItemConsumption(item : ItemConsumptionParamDTO): List<ItemConsumptionReportDTO>{
        return reportRepository.getReportItemConsumption(item)
    }

    override fun getReportDestruction(dest: DestructionReportParamDTO): List<DestructionReportDTO>{
        return reportRepository.getReportDestruction(dest)
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

    override fun getAgeingReport(age:AgeingReportParamDTO): List<AgeingReportDTO>{
        return reportRepository.getAgeingReport(age)
    }

    override fun getShiprocketReport(fromDate: String,toDate: String): List<ShiprocketReportDTO>{
        return reportRepository.getShiprocketReport(fromDate,toDate)
    }

    override fun getVirtualReconciliationReport(fromDate: String, toDate: String, businessUnit: String): List<VirtualReconciliationDTO> {
        return reportRepository.getVirtualReconciliationReport(fromDate,toDate,businessUnit)
    }

}
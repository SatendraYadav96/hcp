package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.ReportService
import com.squer.promobee.service.repository.DispatchPlanRepository
import com.squer.promobee.service.repository.ReportRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class ReportServiceImpl @Autowired constructor(
    private val reportRepository: ReportRepository


): ReportService {

    override fun getReportRecipient(businessUnit: String, divison: String,team:String,statusId:String) : List<RecipientReportDTO>{
        return reportRepository.getReportRecipient(businessUnit, divison, team , statusId)
    }


    override fun getReportPurchase(startDate: String, endDate: String, userId:String, userDesgId:String, businessUnit: String, divison: String): List<PurchaseReportDTO>{
        return reportRepository.getReportPurchase(startDate,endDate,userId,userDesgId,businessUnit,divison)
    }

    override fun getReportDispatches(startDate: String,endDate: String,filter:String,filterPlan:String,userId:String,userDesgId:String,businessUnit: String, divison: String): List<DispatchesReportDTO>{
        return reportRepository.getReportDispatches(startDate,endDate,filter,filterPlan,userId,userDesgId,businessUnit,divison)
    }

    override fun getReportDispatchRegister(startDate: String,endDate: String,userId:String,userDesgId:String, businessUnit: String, divison: String,team:String,statusId: String,filterPlan:Int): List<DispatchRegisterReportDTO>{
        return reportRepository.getReportDispatchRegister(startDate,endDate,userId,userDesgId,businessUnit,divison,team,statusId,filterPlan)
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


    override fun getReportSimpleInventory(businessUnit: String, divison: String,userId:String, userDesgId:String): List<SimpleInventoryReportDTO>{
        return reportRepository.getReportSimpleInventory(businessUnit,divison,userId,userDesgId)
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

}
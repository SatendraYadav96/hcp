package com.squer.promobee.service.repository


import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Recipient
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.util.Date


@Repository
class ReportRepository
    (
    securityUtility: SecurityUtility
): BaseRepository<Recipient>(
    securityUtility = securityUtility
){

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

//    fun getReportRecipient( businessUnit: String, divison: String,team:String,statusId:String) : List<RecipientReportDTO>{
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
//        var data: MutableMap<String, Any> = mutableMapOf()
//        data.put("businessUnit", businessUnit)
//        data.put("divison", divison)
//        data.put("team", team)
//        data.put("statusId", statusId)
//        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportRecipient", data)
//    }

    fun getReportRecipient( businessUnit: String,team:String,statusId:String) : List<RecipientReportDTO>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("businessUnit", businessUnit)
        data.put("team", team)
        data.put("statusId", statusId)
        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportRecipient", data)
    }

    fun getReportPurchase( startDate: String,endDate: String,userId:String,userDesgId:String,businessUnit: String, divison: String) : List<PurchaseReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("startDate", startDate)
        data.put("endDate", endDate)
        data.put("userId", userId)
        data.put("userDesgId", userDesgId)
        data.put("businessUnit", businessUnit)
        data.put("divison", divison)

        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportPurchase", data)
    }


    fun getReportDispatches( startDate: String,endDate: String,filter:Int,filterPlan:Int,userId:String,userDesgId:String, businessUnit: String, division: String) : List<DispatchesReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("StartDate", startDate)
        data.put("EndDate", endDate)
        data.put("Filter", filter)
        data.put("filterplan", filterPlan)
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)
        data.put("BusinessUnit", businessUnit)
        data.put("Division", division)

        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportDispatches", data)
    }


    fun getReportDispatchRegister( startDate: String,endDate: String,userId:String,userDesgId:String, businessUnit: String,division: String ,team:String,statusId: String ,filterPlan:Int) : List<DispatchRegisterReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("StartDate", startDate)
        data.put("EndDate", endDate)
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)
        data.put("BusinessUnit", businessUnit)
        data.put("Division", division)
        data.put("Team", team)
        data.put("StatusID", statusId)
        data.put("Filterplan", filterPlan)

        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportDispatchRegister", data)
    }



    fun getReportDeviation( quarterName:String,fromDate: String,toDate: String,userId:String,userDesgId:String) : List<DeviationReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("QuarterName", quarterName)
        data.put("FromDate", fromDate)
        data.put("ToDate", toDate)
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)


        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportDeviation", data)
    }


    fun getReportItemConsumption( fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String) : List<ItemConsumptionReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("FromDate", fromDate)
        data.put("ToDate", toDate)
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)
        data.put("BusinessUnit", businessUnit)
        data.put("Division", divison)


        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportItemConsumption", data)
    }



    fun getReportDestruction( fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String,statusId: String) : List<DestructionReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("FromDate", fromDate)
        data.put("ToDate", toDate)
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)
        data.put("BusinessUnit", businessUnit)
        data.put("Division", divison)
        data.put("StatusID", statusId)


        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportDestruction", data)
    }




    fun getReportSimpleInventory(businessUnit: String, divison: String,userId:String,userDesgId:String,) : List<SimpleInventoryReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("BusinessUnit", businessUnit)
        data.put("Division", divison)
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)

        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportSimpleInventory", data)
    }


    fun getReportNearToExpirySample(businessUnit: String, divison: String,userId:String,userDesgId:String,type:String) : List<NearToExpiryReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("BusinessUnit", businessUnit)
        data.put("Division", divison)
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)
        data.put("Type", type)

        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportNearToExpirySample", data)
    }

    fun getReportNearToExpiryInput(businessUnit: String, divison: String,userId:String,userDesgId:String,type:String) : List<NearToExpiryReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("BusinessUnit", businessUnit)
        data.put("Division", divison)
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)
        data.put("Type", type)

        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportNearToExpiryInput", data)

    }



    fun getReportSpecialDispatch( fromDate: String,toDate: String,userId:String,userDesgId:String,businessUnit: String,divison: String) : List<SpecialDispatchReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("FromDate", fromDate)
        data.put("ToDate", toDate)
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)
        data.put("BusinessUnit", businessUnit)
        data.put("Division", divison)



        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportSpecialDispatch", data)
    }



    fun getReportDispatchByTeam( year: String,special: String) : List<DispatchByTeamReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("YEAR", year)
        data.put("Special", special)
        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportDispatchByTeam", data)
    }


    fun getItemWiseReport( fromDate: String,toDate: String,businessUnit: String,divison: String) : List<ItemWiseReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("FromDate", fromDate)
        data.put("ToDate", toDate)
        data.put("BusinessUnit", businessUnit)
        data.put("Division", divison)



        return sqlSessionFactory.openSession().selectList("ReportMapper.getItemWiseReport", data)
    }


    fun getStockLedgerReport( fromDate: String,toDate: String,itemId: String) : List<StockLedgerReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("FromDate", fromDate)
        data.put("ToDate", toDate)
        data.put("ItemID", itemId)



        return sqlSessionFactory.openSession().selectList("ReportMapper.getStockLedgerReport", data)
    }


    fun getAgeingReport( userId:String,userDesgId:String,businessUnit: String,divison: String) : List<AgeingReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("UserID", userId)
        data.put("UserDesgID", userDesgId)
        data.put("BusinessUnit", businessUnit)
        data.put("Division", divison)



        return sqlSessionFactory.openSession().selectList("ReportMapper.getAgeingReport", data)
    }


    fun getShiprocketReport( fromDate: String,toDate: String) : List<ShiprocketReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("fromdate", fromDate)
        data.put("enddate", toDate)




        return sqlSessionFactory.openSession().selectList("ReportMapper.getShiprocketReport", data)
    }



}
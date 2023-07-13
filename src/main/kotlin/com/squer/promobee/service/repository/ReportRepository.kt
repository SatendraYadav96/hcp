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


@Repository
class ReportRepository
    (
    securityUtility: SecurityUtility,
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

    fun getReportRecipient( ff: FFReportDTO) : List<RecipientReportDTO>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("businessUnit", ff.businessUnit)
        data.put("team", ff.team)
        ff.statusId?.let { data.put("statusId", it) }




        return sqlSessionFactory.openSession().selectList<RecipientReportDTO>("ReportMapper.getReportRecipient", data)
    }

    fun getReportPurchase( pur : PurchaseReportParamDTO) : List<PurchaseReportDTO>{

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        pur.startDate?.let { data.put("startDate", it) }
        pur.endDate?.let { data.put("endDate", it) }
        pur.userId?.let { data.put("userId", it) }
        pur.userDesgId?.let { data.put("userDesgId", it) }
        data.put("BusinessUnit", pur.businessUnit)
        data.put("Division", pur.divison)

        return sqlSessionFactory.openSession().selectList<PurchaseReportDTO>("ReportMapper.getReportPurchase", data)
    }


    fun getReportDispatches(disp : DispatchesReportParamDto) : List<DispatchesReportDTO>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        disp.startDate?.let { data.put("StartDate", it) }
        disp.endDate?.let { data.put("EndDate", it) }
        disp.filter?.let { data.put("Filter", it) }
        disp.filterPlan?.let { data.put("filterplan", it) }
        disp.userId?.let { data.put("UserID", it) }
        disp.userDesgId?.let { data.put("UserDesgID", it) }
        data.put("BusinessUnit", disp.businessUnit)
        data.put("Division", disp.division)

        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportDispatches", data)
    }


    fun getReportDispatchRegister( dispReg : DispatchRegisterParamDTO) : List<DispatchRegisterReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        dispReg.startDate?.let { data.put("StartDate", it) }
        dispReg.endDate?.let { data.put("EndDate", it) }
        dispReg.userId?.let { data.put("UserID", it) }
        dispReg.userDesgId?.let { data.put("UserDesgID", it) }
        data.put("BusinessUnit", dispReg.businessUnit)
        data.put("Division", dispReg.division)
        dispReg.team?.let { data.put("Team", it) }
        //dispReg.statusId?.let { data.put("StatusID", it) }
        dispReg.filterPlan?.let { data.put("Filterplan", it) }

        return sqlSessionFactory.openSession().selectList<DispatchRegisterReportDTO>("ReportMapper.getReportDispatchRegister", data)
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


    fun getReportItemConsumption( item : ItemConsumptionParamDTO) : List<ItemConsumptionReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        item.fromDate?.let { data.put("FromDate", it) }
        item.toDate?.let { data.put("ToDate", it) }
        item.userId?.let { data.put("UserID", it) }
        item.userDesgId?.let { data.put("UserDesgID", it) }
        data.put("BusinessUnit", item.businessUnit)
        data.put("Division", item.divison)

        var buId = BuDTO()

        var finalResult = mutableListOf<ItemConsumptionReportDTO>()

        var result = mutableListOf<ItemConsumptionReportDTO>()
        var i = 0
        item.divison.forEach {
            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Division",item.divison.get(i))

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1.toString()) }
            item.fromDate?.let { data1.put("FromDate", it) }
            item.toDate?.let { data1.put("ToDate", it) }
            item.userId?.let { data1.put("UserID", it) }
            item.userDesgId?.let { data1.put("UserDesgID", it) }

            result =  sqlSessionFactory.openSession().selectList<ItemConsumptionReportDTO>("ReportMapper.getReportItemConsumption", data1)


            finalResult.addAll(result)

            i++


        }

        return finalResult


    }



    fun getReportDestruction( dest: DestructionReportParamDTO) : List<DestructionReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        dest.fromDate?.let { data.put("FromDate", it) }
        dest.toDate?.let { data.put("ToDate", it) }
        dest.userId?.let { data.put("UserID", it) }
        dest.userDesgId?.let { data.put("UserDesgID", it) }
        data.put("BusinessUnit", dest.businessUnit)
        data.put("Division", dest.divison)
        dest.statusId?.let { data.put("StatusID", it) }



        var buId = BuDTO()

        var finalResult = mutableListOf<DestructionReportDTO>()

        var result = mutableListOf<DestructionReportDTO>()
        var i = 0
        dest.divison.forEach {
            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Division",dest.divison.get(i))

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1.toString()) }
            dest.fromDate?.let { data1.put("FromDate", it) }
            dest.toDate?.let { data1.put("ToDate", it) }
            dest.userId?.let { data1.put("UserID", it) }
            dest.userDesgId?.let { data1.put("UserDesgID", it) }
            dest.statusId?.let { it1 -> data1.put("StatusID", it1) }

            result =  sqlSessionFactory.openSession().selectList<DestructionReportDTO>("ReportMapper.getReportDestruction", data1)


            finalResult.addAll(result)

            i++


        }

        return  finalResult


    }




    fun getReportSimpleInventory(simInv: SimpleInvenotryParamDTO) : List<SimpleInventoryReportDTO>{

//        var result = mutableListOf<SimpleInventoryReportDTO>()
//
//        val parameters: Map<String, Any> = HashMap()
//
//        val businessUnitList: MutableList<ArrayList<String>> = Arrays.asList(simInv.businessUnit) // Example list of business units
//
//        val divisionList: MutableList<ArrayList<String>> = Arrays.asList(simInv.divison) // Example list of divisions


        var data: MutableMap<String, Any> = mutableMapOf()

            data.put("BusinessUnit",simInv.businessUnit)
            data.put("Division", simInv.divison)
            simInv.userId?.let { data.put("UserID", it) }
            simInv.userDesgId?.let { data.put("UserDesgID", it) }

        return sqlSessionFactory.openSession().selectList<SimpleInventoryReportDTO>("ReportMapper.getReportSimpleInventory", data)












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


    fun getAgeingReport( age:AgeingReportParamDTO) : List<AgeingReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        age.userId?.let { data.put("UserID", it) }
        age.userDesgId?.let { data.put("UserDesgID", it) }
        data.put("BusinessUnit", age.businessUnit)
        data.put("Division", age.divison)



        var buId = BuDTO()

        var finalResult = mutableListOf<AgeingReportDTO>()

        var result = mutableListOf<AgeingReportDTO>()
        var i = 0
        age.divison.forEach {
            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Division",age.divison.get(i))

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1.toString()) }
            age.userId?.let { data1.put("UserID", it) }
            age.userDesgId?.let { data1.put("UserDesgID", it) }


            result =  sqlSessionFactory.openSession().selectList<AgeingReportDTO>("ReportMapper.getAgeingReport", data1)


            finalResult.addAll(result)

            i++


        }

        return  finalResult
    }


    fun getShiprocketReport( fromDate: String,toDate: String) : List<ShiprocketReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("fromdate", fromDate)
        data.put("enddate", toDate)




        return sqlSessionFactory.openSession().selectList("ReportMapper.getShiprocketReport", data)
    }


    fun getVirtualReconciliationReport(fromDate: String, toDate: String, businessUnit: String): List<VirtualReconciliationDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("fromdate", fromDate)
        data.put("enddate", toDate)
        data.put("BusinessUnit", businessUnit)




        return sqlSessionFactory.openSession().selectList("ReportMapper.getVirtualReconciliationReport", data)
    }


}
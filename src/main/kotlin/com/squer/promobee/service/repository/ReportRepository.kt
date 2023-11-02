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
import java.text.SimpleDateFormat
import java.util.*


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

        var buId = BuDTO()

        var finalResult = mutableListOf<DispatchesReportDTO>()

        var result = mutableListOf<DispatchesReportDTO>()

        var i = 0
        disp.division.forEach {

            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Division",disp.division.get(i))

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1.toString()) }
            disp.startDate?.let { data1.put("StartDate", it) }
            disp.endDate?.let { data1.put("EndDate", it) }
            disp.userId?.let { data1.put("UserID", it) }
            disp.userDesgId?.let { data1.put("UserDesgID", it) }
            disp.filter?.let { data1.put("Filter", it) }
            disp.filterPlan?.let { data1.put("filterplan", it) }

            result =  sqlSessionFactory.openSession().selectList<DispatchesReportDTO>("ReportMapper.getReportDispatches", data1)


            finalResult.addAll(result)

            i++


        }

        return finalResult
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
        data.put("StatusID","00000000-0000-0000-0000-000000000000")

        var buId = BuDTO()
        var divId = BuDTO()


        var finalResult = mutableListOf<DispatchRegisterReportDTO>()

        var result = mutableListOf<DispatchRegisterReportDTO>()
        var i = 0
        dispReg.team.forEach {
            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Team",dispReg.team.get(i))

            divId = sqlSessionFactory.openSession().selectOne("ReportMapper.getDivisionForReport",dispReg.team.get(i))

            divId.divId?.let { it1 -> data1.put("Division", it1) }

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport", divId.divId)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1.toString()) }


            dispReg.startDate?.let { data1.put("StartDate", it) }
            dispReg.endDate?.let { data1.put("EndDate", it) }
            dispReg.userId?.let { data1.put("UserID", it) }
            dispReg.userDesgId?.let { data1.put("UserDesgID", it) }
            dispReg.filterPlan?.let { data1.put("Filterplan", it) }
            data1.put("StatusID","00000000-0000-0000-0000-000000000000")

            result =  sqlSessionFactory.openSession().selectList<DispatchRegisterReportDTO>("ReportMapper.getReportDispatchRegister", data1)


            finalResult.addAll(result)

            i++


        }

        return finalResult
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

//            data1.put("Division",dest.divison.get(i))

            data1.put("Division",it)
            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1.toString()) }
            dest.fromDate?.let { data1.put("FromDate", it) }
            dest.toDate?.let { data1.put("ToDate", it) }
//            dest.userId?.let { data1.put("UserID", it) }
//            dest.userDesgId?.let { data1.put("UserDesgID", it) }
            dest.statusId?.let { it1 -> data1.put("StatusID", it1) }

            result =  sqlSessionFactory.openSession().selectList<DestructionReportDTO>("ReportMapper.getReportDestructionNew", data1)


            finalResult.addAll(result)

            i++


        }

        return  finalResult


    }




    fun getReportSimpleInventory(simInv: SimpleInvenotryParamDTO) : List<SimpleInventoryReportDTO>{

        var data: MutableMap<String, Any> = mutableMapOf()

            data.put("BusinessUnit",simInv.businessUnit)
            data.put("Division", simInv.divison)
            simInv.userId?.let { data.put("UserID", it) }
            simInv.userDesgId?.let { data.put("UserDesgID", it) }
        var buId = BuDTO()

        var finalResult = mutableListOf<SimpleInventoryReportDTO>()

        var result = mutableListOf<SimpleInventoryReportDTO>()

        var i = 0

        simInv.divison.forEach {
            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Division",simInv.divison.get(i))

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1) }
            simInv.userId?.let { data1.put("UserID", it) }
            simInv.userDesgId?.let { data1.put("UserDesgID", it) }



            result =  sqlSessionFactory.openSession().selectList<SimpleInventoryReportDTO>("ReportMapper.getReportSimpleInventory", data1)


            finalResult.addAll(result)

            i++


        }

        return  finalResult


    }


    fun getReportNearToExpirySample(sample: NearExpiryReportParamDTO) : List<NearToExpiryReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("BusinessUnit", sample.businessUnit)
        data.put("Division", sample.divison)
        sample.userId?.let { data.put("UserID", it) }
        sample.userDesgId?.let { data.put("UserDesgID", it) }
        sample.type?.let { data.put("Type", it) }

        var buId = BuDTO()

        var finalResult = mutableListOf<NearToExpiryReportDTO>()

        var result = mutableListOf<NearToExpiryReportDTO>()

        var i = 0

        sample.divison.forEach {
            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Division",sample.divison.get(i))

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1) }
            sample.userId?.let { data1.put("UserID", it) }
            sample.userDesgId?.let { data1.put("UserDesgID", it) }
            sample.type?.let { data1.put("Type", it) }


            result =  sqlSessionFactory.openSession().selectList<NearToExpiryReportDTO>("ReportMapper.getReportNearToExpirySample", data1)


            finalResult.addAll(result)

            i++


        }

        return  finalResult
    }

    fun getReportNearToExpiryInput(input: NearExpiryReportParamDTO) : List<NearToExpiryReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("BusinessUnit", input.businessUnit)
        data.put("Division", input.divison)
        input.userId?.let { data.put("UserID", it) }
        input.userDesgId?.let { data.put("UserDesgID", it) }
        input.type?.let { data.put("Type", it) }

        var buId = BuDTO()

        var finalResult = mutableListOf<NearToExpiryReportDTO>()

        var result = mutableListOf<NearToExpiryReportDTO>()
        var i = 0
        input.divison.forEach {
            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Division",input.divison.get(i))

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1) }
            input.userId?.let { data1.put("UserID", it) }
            input.userDesgId?.let { data1.put("UserDesgID", it) }
            input.type?.let { data1.put("Type", it) }


            result =  sqlSessionFactory.openSession().selectList<NearToExpiryReportDTO>("ReportMapper.getReportNearToExpiryInput", data1)


            finalResult.addAll(result)

            i++


        }

        return  finalResult

    }



    fun getReportSpecialDispatch( speDisp:SpecialDispatchReportParamDTO) : List<SpecialDispatchReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        speDisp.fromDate?.let { data.put("FromDate", it) }
        speDisp.toDate?.let { data.put("ToDate", it) }
        speDisp.userId?.let { data.put("UserID", it) }
        speDisp.userDesgId?.let { data.put("UserDesgID", it) }
        data.put("BusinessUnit", speDisp.businessUnit)
        data.put("Division", speDisp.divison)

        var buId = BuDTO()

        var finalResult = mutableListOf<SpecialDispatchReportDTO>()

        var result = mutableListOf<SpecialDispatchReportDTO>()
        var i = 0
        speDisp.divison.forEach {
            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Division",speDisp.divison.get(i))

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1) }
            speDisp.fromDate?.let { data1.put("FromDate", it) }
            speDisp.toDate?.let { data1.put("ToDate", it) }
            speDisp.userId?.let { data1.put("UserID", it) }
            speDisp.userDesgId?.let { data1.put("UserDesgID", it) }


            result =  sqlSessionFactory.openSession().selectList<SpecialDispatchReportDTO>("ReportMapper.getReportSpecialDispatch", data1)


            finalResult.addAll(result)

            i++


        }

        return  finalResult
    }



    fun getReportDispatchByTeam( year: String,special: String) : List<DispatchByTeamReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("YEAR", year)
        data.put("Special", special)
        return sqlSessionFactory.openSession().selectList("ReportMapper.getReportDispatchByTeam", data)
    }


    fun getItemWiseReport( item : ItemWiseReportParamDTO) : List<ItemWiseReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        item.fromDate?.let { data.put("FromDate", it) }
        item.toDate?.let { data.put("ToDate", it) }
        data.put("BusinessUnit", item.businessUnit)
        data.put("Division", item.divison)

        var buId = BuDTO()

        var finalResult = mutableListOf<ItemWiseReportDTO>()

        var result = mutableListOf<ItemWiseReportDTO>()
        var i = 0
        item.divison.forEach {
            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("Division",item.divison.get(i))

            buId = sqlSessionFactory.openSession().selectOne("ReportMapper.getBusinessUnitForReport",data1)

            buId.buId?.let { it1 -> data1.put("BusinessUnit", it1) }
            item.fromDate?.let { data1.put("FromDate", it) }
            item.toDate?.let { data1.put("ToDate", it) }


            result =  sqlSessionFactory.openSession().selectList<ItemWiseReportDTO>("ReportMapper.getItemWiseReport", data1)


            finalResult.addAll(result)

            i++


        }

        return  finalResult
    }


    fun getStockLedgerReport( fromDate: String,toDate: String,itemId: String) : List<StockLedgerReportDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("FromDate", fromDate)
        data.put("ToDate", toDate)
        data.put("ItemID", itemId)



            return sqlSessionFactory.openSession().selectList<StockLedgerReportDTO>("ReportMapper.getStockLedgerReport", data)
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


    fun getVirtualReconciliationReport(quarter: String, year: String, businessUnit: String): List<VirtualReconciliationDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()

        var (fromDate, toDate) = getDates(quarter, year)

        var sdf = SimpleDateFormat(
            "EE MMM dd HH:mm:ss z yyyy",
            Locale.ENGLISH
        )

        var parsedDate: Date = sdf.parse(fromDate.toString())

        var startDate = SimpleDateFormat("yyyy-MM-dd")

        var startDate1 = startDate.format(parsedDate)



        var parsedDate1: Date = sdf.parse(toDate.toString())

        var endDate = SimpleDateFormat("yyyy-MM-dd")

        var endDate1 = endDate.format(parsedDate1)




      data.put("fromdate", startDate1)
       data.put("enddate", endDate1)
        data.put("BusinessUnit", businessUnit)




        return sqlSessionFactory.openSession().selectList("ReportMapper.getVirtualReconciliationReport", data)
    }

    fun getBatchReconciliation(): List<BatchReconciliationDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()

        return sqlSessionFactory.openSession().selectList<BatchReconciliationDTO>("ReportMapper.getBatchReconciliation")
    }




    fun getDates(quarter: String, year: String): Pair<Date, Date> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year.toInt())
        cal.set(Calendar.MONTH, when (quarter) {
            "001" -> Calendar.JANUARY
            "002" -> Calendar.APRIL
            "003" -> Calendar.JULY
            "004" -> Calendar.OCTOBER
            else -> throw IllegalArgumentException("Invalid quarter: $quarter")
        })
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val fromDate = cal.time
        cal.add(Calendar.MONTH, 2)
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        val toDate = cal.time
        return Pair(fromDate, toDate)
    }










}
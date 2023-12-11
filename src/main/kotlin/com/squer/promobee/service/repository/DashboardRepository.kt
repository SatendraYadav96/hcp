package com.squer.promobee.service.repository


import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.UploadLog
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.YearMonth

@Repository
class
DashboardRepository(
    securityUtility: SecurityUtility
): BaseRepository<UploadLog>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    //HUB

    fun getPendingDispatch (): List<PendingDispacthDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getPendingDispatch", data)
    }

    fun getHubNearExpiry (): List<HubNearExpiryDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getHubNearExpiry", data)
    }


    fun getHubPendingRevalidation (): List<HUBPendingRevalidationDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getHubPendingRevalidation", data)
    }

        fun getHubGrnErrorLog (): List<HubGrnErrorLogDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getHubGrnErrorLog", data)
    }


    fun getItemExpiredDetails (): List<ItemExpiredDetailsDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getItemExpiredDetails", data)
    }

    //BEX



    fun batchReconciliation (): List<BatchReconciliationDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.batchReconciliation", data)
    }


    fun bexManagementDashboard(month: Int, year: Int, toMonth: Int, toYear: Int, type: String): Any? {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        //var date = LocalDate(year, month, 1).toString()
        var sDate = YearMonth.of(year,month)
        var startMonth: LocalDate = sDate.atDay(1)

        var eDate = YearMonth.of(toYear,toMonth)
        var endMonth = eDate.atEndOfMonth()

        //var managementData : List<BuWiseMonthlyPlansDTO> = ArrayList<BuWiseMonthlyPlansDTO>()
        if(type == "1"){
            data.put("fromdate", startMonth)
            data.put("enddate", endMonth)

           // managementData =
                return sqlSessionFactory.openSession().selectList<BuWiseMonthlyPlansDTO>("DashboardMapper.buWiseMonthlyPlans",data)


        }
       // return managementData


        //var managementData1 : List<BUSpecialDisatchDTO> = ArrayList<BUSpecialDisatchDTO>()

        if(type == "2"){
            data.put("fromdate", startMonth)
            data.put("enddate", endMonth)

            //managementData1 =
                return sqlSessionFactory.openSession().selectList<BUSpecialDisatchDTO>("DashboardMapper.buWiseSpecialDispatch",data)

        }
       //return managementData1

        //var managementData2 : List<blockUnblockDetailsDTO> = ArrayList<blockUnblockDetailsDTO>()

        if(type == "3"){
            data.put("MONTH",month)
            data.put("YEAR",year)
           // managementData2 =
                return sqlSessionFactory.openSession().selectList<blockUnblockDetailsDTO>("DashboardMapper.blockUnblockFFDetails",data)
        }
        //return managementData2


        //var managementData3 : List<SampleInputExpiredDTO> = ArrayList<SampleInputExpiredDTO>()

        if(type == "4"){
            data.put("startdate", startMonth)
            data.put("enddate", endMonth)

            //managementData3 =
                return sqlSessionFactory.openSession().selectList<SampleInputExpiredDTO>("DashboardMapper.sampleInputExpired",data)
        }
        //return managementData3

        //var managementData4 : List<HoDispatchDetailsDTO> = ArrayList<HoDispatchDetailsDTO>()

        if(type == "5"){
            data.put("fromdate", startMonth)
            data.put("enddate", endMonth)

            //managementData4 =
                return sqlSessionFactory.openSession().selectList<HoDispatchDetailsDTO>("DashboardMapper.hoDispatchDetails",data)
        }
        return Any()



    }


    fun dispatchesMonthWise (): List<DashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.dispatchesMonthWise", data)
    }

    fun specialCourierCostMonthWise (): List<DashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.specialCourierCostMonthWise", data)
    }












}
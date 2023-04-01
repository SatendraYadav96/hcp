package com.squer.promobee.service.repository

import com.squer.promobee.api.v1.enums.InvoiceStatusEnum
import com.squer.promobee.controller.dto.DataModelInvoiceDetailsDTO
import com.squer.promobee.controller.dto.GroupingInvoiceDetailsDTO
import com.squer.promobee.controller.dto.PickingSlipDTO
import com.squer.promobee.controller.dto.TeamInvoiceDTO
import com.squer.promobee.controller.dto.TeamPlanInvoiceDTO
import com.squer.promobee.mapper.DispatchPlanMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.DispatchPlan
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
class DispatchPlanRepository(
        securityUtility: SecurityUtility
): BaseRepository<DispatchPlan>(
        securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getPlanHeaderSelect(monthPlan: Int, yearPlan: Int, userId: String, isSpecial: Int ?= null): DispatchPlan?{
      //  return dispatchPlanMapper.getPlanHeaderSelect(monthPlan, yearPlan, userId)
        var data : MutableMap<String, Any> = mutableMapOf()
        data.put("monthPlan", monthPlan)
        data.put("yearPlan", yearPlan)
        data.put("userId", userId)
        if(isSpecial!=null)
            data.put("isSpecial",isSpecial)
        return sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getPlanHeaderSelect", data)
    }

    fun insertPlan(plan: DispatchPlan){
        sqlSessionFactory.openSession().insert("DispatchPlanMapper.insertPlan", plan)
    }

    fun getPickingList(month: Int, year: Int, dispatchType: String): List<PickingSlipDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        if(dispatchType == "Virtual"){
            data.put("month", month)
            data.put("year", year)
            data.put("dispatchType", 1)
            return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getPickListDetailForVirtual", data)
        }
        else if(dispatchType == "1"){
            data.put("month", month)
            data.put("year", year)
            data.put("dispatchType", dispatchType.toInt())
            return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getPickListDetailsForSpecial", data)
        }else{
            data.put("month", month)
            data.put("year", year)
            data.put("dispatchType", dispatchType.toInt())
            return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getPickListDetailsForMonthly", data)
        }
    }

    fun getMonthlyDispatchSearch( month: Int, year: Int) : List<TeamInvoiceDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("month", month)
        data.put("year", year)
        data.put("isSpecial", 0)
        return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getPlanInvoice", data)
    }

    fun getEmployeeInvoiceDetails(month: Int, year: Int, isSpecialDisp: String, teamId: String, status: String): List<DataModelInvoiceDetailsDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("month", month)
        data.put("year", year)
        data.put("isSpecialDisp", isSpecialDisp.toInt())
        data.put("teamId", teamId)
        data.put("status", status)
        if(status == InvoiceStatusEnum.GENERATED_PRINTED.id || status == InvoiceStatusEnum.CANCELLED.id || status == InvoiceStatusEnum.REDIRECTED.id){
            return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.GetEmployeeInvoiceDetail_GP_C_R", data)
        }else{
            return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getEmployeeInvoiceDetailDraft", data)
        }
    }

    fun getGroupingInvoiceForHUB(fromDate: Date, toDate: Date, invoiceNumber: Int): List<GroupingInvoiceDetailsDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("FromDate", fromDate)
        data.put("ToDate", toDate)
        data.put("InvoiceNumber", invoiceNumber)
        return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getGroupingInvoiceForHUB", data)
    }

    fun getInvoicesForGrouping(fromDate: Date, toDate: Date, invoiceNumber: Int): List<GroupingInvoiceDetailsDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("FromDate", fromDate)
        data.put("ToDate", toDate)
        data.put("InvoiceNumber", invoiceNumber)
        return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getInvoicesForGrouping", data)
    }

    fun getSpecialDispatchSearch(year: Int,month: Int) : List<TeamPlanInvoiceDTO>{
        println(year);
        println(month);
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("year", year)
        data.put("month", month)
        data.put("isSpecial", 1)
        return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getSpecialDispatchListing", data)
    }

        fun getSpecialEmployeeInvoiceDetails(planId: String, status: String): List<DataModelInvoiceDetailsDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("planId", planId)
        data.put("status", status)
        if(status == InvoiceStatusEnum.GENERATED_PRINTED.id || status == InvoiceStatusEnum.CANCELLED.id || status == InvoiceStatusEnum.REDIRECTED.id){
            return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.GetSpecialInvoiceDetail_GP_C_R", data)
        }else{
            return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.GetSpecialDispatchInvoiceDetailDraft", data)
        }
    }

    fun getVirtualDispatchSearch( month: Int, year: Int) : List<TeamPlanInvoiceDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("month", month)
        data.put("year", year)
       // data.put("isSpecial", 1)
       // data.put("isVirtual", 1)
        return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getVirtualDispatchListing", data)
    }

}
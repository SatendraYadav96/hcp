package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.DispatchPlan
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Select

@Mapper
interface DispatchPlanMapper : BaseMapper<DispatchPlan>{

    /*@Select("<script>SELECT ID_DIP, ID_OWNER_USR_DIP, MONTH_DIP, YEAR_DIP," +
            " ID_STATUS_SLV_DIP, SUBMISSION_DATE_DIP, APPROVAL_DATE_DIP," +
            " IS_SPECIAL_DIP, REMARKS_DIP, CREATED_ON_DIP, CREATED_BY_DIP," +
            " UPDATED_ON_DIP, UPDATED_BY_DIP, ID_INVOICE_STATUS_SLV_DIP," +
            " QTR_DIP, IS_VIRTUAL_DIP from DISPATCH_PLAN_DIP" +
            " <where>" +
            " <if test=\"monthPlan!=null\">  MONTH_DIP = #{monthPlan} </if>" +
            " <if test=\"yearPlan!=null\"> and YEAR_DIP = #{yearPlan} </if>" +
            " <if test=\"userId!=null\"> and ID_OWNER_USR_DIP = #{userId} </if>" +
            "  </where></script>")
    @ResultMap("dispatchPlanResultMap")
    fun getPlanHeaderSelect(monthPlan: Int, yearPlan: Int, userId: String): DispatchPlan*/

   /* @Insert(" INSERT INTO DISPATCH_PLAN_DIP(ID_DIP, ID_OWNER_USR_DIP, MONTH_DIP, YEAR_DIP," +
            " ID_STATUS_SLV_DIP," +
            " IS_SPECIAL_DIP, REMARKS_DIP, CREATED_ON_DIP, CREATED_BY_DIP," +
            " UPDATED_ON_DIP, UPDATED_BY_DIP, ID_INVOICE_STATUS_SLV_DIP," +
            " QTR_DIP, IS_VIRTUAL_DIP) VALUES( #{id}, #{owner.id}, #{month}, #{year}, #{planStatus.id}, #{isSpecial}, #{remarks}, GETDATE(), #{createdBy}, GETDATE(), #{updatedBy}, #{invoiceStatus.id}, #{quarterlyPlan}, #{isVirtual})")
    fun insertPlan(plan: DispatchPlan)*/
}
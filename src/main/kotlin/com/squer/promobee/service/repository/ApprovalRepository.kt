package com.squer.promobee.service.repository


import com.squer.promobee.api.v1.enums.UserLovEnum
import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.controller.dto.MontlyApprovalBexDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.DispatchPlan
import com.squer.promobee.service.repository.domain.Users
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ApprovalRepository(
    securityUtility: SecurityUtility
): BaseRepository<Users>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getMonthlyApprovalForBex(month: Int, year: Int, userId: String, userDesgId: String): List<MontlyApprovalBexDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("Month", month)
        data.put("Year", year)
        data.put("UserID", userId)
        data.put("UserDesignation", userDesgId)
        return sqlSessionFactory.openSession().selectList("ApprovalMapper.getMonthlyApprovalForBex", data)
    }

    fun getDispatchPlanById(id: String): DispatchPlan {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("id", id)

        return sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getDispatchPlanById",data)
    }


    fun getMonthlyApprovalDetails(userId: String, planId: String): List<AllocationInventoryDetailsWithCostCenterDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        var plan = getDispatchPlanById(planId)

        var isuserRbm = (plan.owner!!.id == UserLovEnum.REGIONAL_BUSINESS_MANAGER.id  || plan.owner!!.id == UserLovEnum.NATIONAL_SALES_MANAGER.id)

        var allocationDetails = AllocationInventoryDetailsWithCostCenterDTO()

        if(isuserRbm){

            data.put("USERID", userId)
            data.put("RBMPLANID", planId)


            allocationDetails = return sqlSessionFactory.openSession().selectList("ApprovalMapper.allocationDetailsRbm",data)

        }else{

            data.put("UserID", userId)
            data.put("PlanID", planId)

            allocationDetails = return sqlSessionFactory.openSession().selectList("ApprovalMapper.allocationDetailsBrandManager", data)
        }


        return allocationDetails



    }













}
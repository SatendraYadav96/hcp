package com.squer.promobee.service.repository

import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.controller.dto.InventoryDTO
import com.squer.promobee.controller.dto.InventoryReversalDTO
import com.squer.promobee.controller.dto.RecipientReportDTO
import com.squer.promobee.mapper.InventoryMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Inventory
import com.squer.promobee.service.repository.domain.Vendor
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository

@Repository
class InventoryRepository @Autowired constructor(
        securityUtility: SecurityUtility
): BaseRepository<Inventory>(
        securityUtility = securityUtility
){

    @Autowired
    lateinit var inventoryMapper: InventoryMapper

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    fun insertInventory(inventory: Inventory){
       sqlSessionFactory.openSession().insert("InventoryMapper.insertInventory", inventory)
    }

    fun getMonthlyAllocation(planId: String, userId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO> {
        var data : MutableMap<String, String> = mutableMapOf()
        data.put("planId", planId)
        data.put("userId", userId)
        return sqlSessionFactory.openSession().selectList<AllocationInventoryDetailsWithCostCenterDTO>("InventoryMapper.getAllocationInventoryWithCostcenter", data)
    }

    fun getInventoryDistributionByTeamForPlan(planId: String): List<MutableMap<String, Any>>{
        return sqlSessionFactory.openSession().selectList("InventoryMapper.planDistributionForItemSelect", planId)
    }


    fun editUnitAllocation(inv: InventoryDTO) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        inv.invId?.let { data.put("id", it) }
        inv.isUnitAllocation?.let { data.put("isUnitAllocation", it) }

        data.put("updatedBy", user.id)

        sqlSessionFactory.openSession().update("InventoryMapper.editUnitAllocation",data)


    }


    fun blockItem(inv: InventoryDTO) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        inv.invId?.let { data.put("id", it) }
        inv.isBlockItem?.let { data.put("isBlockItem", it) }

        data.put("updatedBy", user.id)

        sqlSessionFactory.openSession().update("InventoryMapper.blockItem",data)


    }


    fun searchInventory(  isExhausted: Boolean, isPopup:Int) : List<InventoryDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
//        data.put("name", name)
        data.put("isExhausted", isExhausted)
        data.put("isPopup", isPopup)
        return sqlSessionFactory.openSession().selectList("InventoryMapper.searchInventory", data)
    }


    fun getInventoryReversalHistory(  invId: String) : List<InventoryReversalDTO> {
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("id", invId)

         return sqlSessionFactory.openSession().selectList("InventoryMapper.getInventoryReversalHistory", data)
    }



}
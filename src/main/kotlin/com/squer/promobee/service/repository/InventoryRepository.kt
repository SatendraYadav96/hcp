package com.squer.promobee.service.repository

import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.mapper.InventoryMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Inventory
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
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
}
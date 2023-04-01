package com.squer.promobee.service

import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.service.repository.domain.Inventory

interface InventoryService {

    fun insertInventory(inventory: Inventory)

    fun getMonthlyAllocation(planId: String, userId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO>

    fun getInventoryDistributionByTeamForPlan(planId: String): List<MutableMap<String, Any>>

}
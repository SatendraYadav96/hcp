package com.squer.promobee.service

import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.controller.dto.InventoryDTO
import com.squer.promobee.controller.dto.InventoryReversalDTO
import com.squer.promobee.controller.dto.RecipientReportDTO
import com.squer.promobee.service.repository.domain.Inventory
import org.springframework.web.bind.annotation.PathVariable

interface InventoryService {

    fun insertInventory(inventory: Inventory)

    fun getMonthlyAllocation(planId: String, userId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO>

    fun getInventoryDistributionByTeamForPlan(planId: String): List<MutableMap<String, Any>>

    fun editUnitAllocation(inv:InventoryDTO)

    fun blockItem(inv:InventoryDTO)

    fun searchInventory( isExhausted: Boolean, isPopup:Int) : List<InventoryDTO>

    fun getInventoryReversalHistory( invId: String) : List<InventoryReversalDTO>

}
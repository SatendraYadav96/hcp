package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.service.InventoryService
import com.squer.promobee.service.repository.InventoryRepository
import com.squer.promobee.service.repository.domain.Inventory
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class InventoryServiceImpl @Autowired constructor(
        private val inventoryRepository: InventoryRepository
): InventoryService{

    override fun insertInventory(inventory: Inventory) {
        inventoryRepository.insertInventory(inventory)
    }

    override fun getMonthlyAllocation(planId: String, userId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO> {
        return inventoryRepository.getMonthlyAllocation(planId, userId)
    }

    override fun getInventoryDistributionByTeamForPlan(planId: String): List<MutableMap<String, Any>>{
        return inventoryRepository.getInventoryDistributionByTeamForPlan(planId)
    }
}
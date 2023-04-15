package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.controller.dto.InventoryDTO
import com.squer.promobee.controller.dto.InventoryReversalDTO
import com.squer.promobee.controller.dto.RecipientReportDTO
import com.squer.promobee.service.InventoryService
import com.squer.promobee.service.repository.InventoryRepository
import com.squer.promobee.service.repository.domain.Inventory
import com.squer.promobee.service.repository.domain.Vendor
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

    override fun getInventoryDistributionByTeamForPlan(planId: String): List<MutableMap<String, Any>> {
        return inventoryRepository.getInventoryDistributionByTeamForPlan(planId)
    }

        override fun editUnitAllocation(inv: InventoryDTO) {
             inventoryRepository.editUnitAllocation(inv)
        }

    override fun blockItem(inv: InventoryDTO) {
        inventoryRepository.blockItem(inv)
    }


    override fun searchInventory( isExhausted: Boolean, isPopup:Int) : List<InventoryDTO>{
        return inventoryRepository.searchInventory( isExhausted, isPopup)
    }

    override fun getInventoryReversalHistory( invId: String) : List<InventoryReversalDTO> {
        return inventoryRepository.getInventoryReversalHistory(invId )
    }

//    override fun reverseInventory(inv: InventoryReversalDTO) {
//        inventoryRepository.reverseInventory(inv)
//    }

}
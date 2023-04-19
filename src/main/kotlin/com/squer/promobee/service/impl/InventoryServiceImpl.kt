package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.*
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


    override fun searchInventory( isExhausted: Boolean, isPopup:Int) : List<Inventory>{
        return inventoryRepository.searchInventory( isExhausted, isPopup)
    }

    override fun getInventoryReversalHistory( invId: String) : List<InventoryReversalDTO> {
        return inventoryRepository.getInventoryReversalHistory(invId )
    }

    override fun getInventoryReversalBMAllocation( invId: String) : List<InventoryReversalDTO> {
        return inventoryRepository.getInventoryReversalBMAllocation(invId )
    }

    override fun getInventoryById( invId: String): Inventory  {
        return inventoryRepository.getInventoryById(invId )
    }


    override fun getMaxInvoiceNo( ): Int?  {
        return inventoryRepository.getMaxInvoiceNo( )
    }


    override fun reverseInventory(inv: InventoryReversalDTO) {
        inventoryRepository.reverseInventory(inv)
    }

    override fun switchInventory(inv: SwitchInventoryDTO) {
        inventoryRepository.switchInventory(inv)
    }

}
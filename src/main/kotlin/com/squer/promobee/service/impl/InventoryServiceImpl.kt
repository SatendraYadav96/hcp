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

    override fun getPickList(teamId: String, month: Int, year: Int, isSpecial: Int): List<PickListDTO> {
        return inventoryRepository.getPickList(teamId,month,year,isSpecial)
    }

    override fun getPickListVirtual(teamId: String, month: Int, year: Int, isSpecial: Int): List<PickListDTO> {
        return inventoryRepository.getPickListVirtual(teamId,month,year,isSpecial)
    }

    override fun getPickListStatusByBM(teamId: String, month: Int, year: Int): List<BrandManagerPlanStatusDTO> {
        return inventoryRepository.getPickListStatusByBM(teamId,month,year)
    }

    override fun getSpecialDispatchListForInvoicing(planId: String, status: String): List<DataModelInvoiceDetailsDTO> {
        return inventoryRepository.getSpecialDispatchListForInvoicing(planId,status)
    }

    override fun getVirtualDispatchListForInvoicing(planId: String, status: String): List<DataModelInvoiceDetailsDTO> {
        return inventoryRepository.getVirtualDispatchListForInvoicing(planId,status)
    }

    override fun getEmployeeInvoicePopupDetails(month: Int, year: Int, isSpecial: Int, employeeId: String, invoiceHeaderId: String): List<EmployeeInvoiceDetailsPopupDTO> {
        return inventoryRepository.getEmployeeInvoicePopupDetails(month, year,isSpecial,employeeId,invoiceHeaderId)
    }


}
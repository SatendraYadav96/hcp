package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.Inventory
import org.springframework.web.bind.annotation.PathVariable

interface InventoryService {

    fun insertInventory(inventory: Inventory)

    fun getMonthlyAllocation(planId: String, userId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO>

    fun getInventoryDistributionByTeamForPlan(planId: String): List<MutableMap<String, Any>>

    fun editUnitAllocation(inv:InventoryDTO)

    fun blockItem(inv:InventoryDTO)

    fun searchInventory( isExhausted: Boolean, isPopup:Int) : List<Inventory>

    fun getInventoryReversalHistory( invId: String) : List<InventoryReversalDTO>

    fun getInventoryReversalBMAllocation( invId: String) : List<InventoryReversalDTO>

    fun getInventoryById( invId: String): Inventory

    fun getMaxInvoiceNo( ): Int?

    fun reverseInventory(inv:InventoryReversalDTO)

    fun switchInventory(inv: SwitchInventoryDTO)

    fun getPickList(  teamId: String ,  month: Int ,  year: Int,  isSpecial: Int): List<PickListDTO>

    fun getPickListVirtual(  teamId: String ,  month: Int ,  year: Int,  isSpecial: Int): List<PickListDTO>






}
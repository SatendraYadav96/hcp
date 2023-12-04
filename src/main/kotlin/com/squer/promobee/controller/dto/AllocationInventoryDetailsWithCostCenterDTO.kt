package com.squer.promobee.controller.dto

import com.squer.promobee.service.repository.domain.DispatchPlan
import java.util.*

class AllocationDetailsDTO{
    var item: List<AllocationInventoryDetailsWithCostCenterDTO>?= null
    var plan: DispatchPlan?= null
    var planSubmitted: String? = null
}

class AllocationInventoryDetailsWithCostCenterDTO {
    var isItem: Int?= null
    var costCenterID : String ?= null
    var costCenterName: String ?= null
    var itemID : String ?= null
    var itemName: String ?= null
    var stock: Int?= null
    var expiryDate : Date ?= null
    var poNo: String ?= null
    var packSize : Int? = null
    var quantityAllocated : Int? = null
    var planId: String ?= null
    var inventoryId: String ?= null
    var isBlockItem: Int ?= null
    var qtyReceived: Int ?= null
    var qtyAllocated: Int ?= null
    var qtyDispatched: Int ?= null
}
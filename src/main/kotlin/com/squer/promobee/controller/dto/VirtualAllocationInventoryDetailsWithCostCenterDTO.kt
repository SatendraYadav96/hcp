package com.squer.promobee.controller.dto

import java.util.*

class VirtualAllocationInventoryDetailsWithCostCenterDTO {
    var isItem: Int?= null
    var costCenterID : String ?= null
    var costCenterName: String ?= null
    var itemID : String ?= null
    var itemName: String ?= null
    var stock: Int?= null
    var expiryDate : Date?= null
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
package com.squer.promobee.controller.dto

import com.squer.promobee.security.domain.NamedSquerEntity
import java.util.Date

class InventoryReversalDTO {
    var invId: String ?= null
    var reversalDate: String ?= null
    var remarks: String ? = null
    var quantity: String ? = null
    var qtyDispatched: String ? = null
    var userName: String ? = null
    var UserId: String ? = null

}
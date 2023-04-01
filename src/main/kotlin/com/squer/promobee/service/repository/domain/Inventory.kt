package com.squer.promobee.service.repository.domain

import com.fasterxml.jackson.databind.util.Named
import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.SquerEntity
import java.util.Date

class Inventory: AuditableEntity() {
    var item: NamedSquerEntity?= null
    var grn: SquerEntity?= null
    var packSize: Int?= null
    var poNo: String?= null
    var ccmID: NamedSquerEntity?= null
    var limid: String?= null
    var postingDate: Date?= null
    var expiryDate: Date?= null
    var categoryId: NamedSquerEntity?= null
    var medicalCode: String?= null
    var vendorId: NamedSquerEntity?= null
    var ratePerUnit: Double?= null
    var qtyReceived: Int?= null
    var qtyAllocated: Int?= null
    var qtyDispatched: Int?= null
    var numBoxes: Double?= null
    var isUnitAllocation: Int?= null
    var batchNo: String?= null
    var isBlockItem: Int?= null
    var comment: String?= null
    var hsnCode: String?= null
    var rate: Double?= null
    var units: String?= null
}
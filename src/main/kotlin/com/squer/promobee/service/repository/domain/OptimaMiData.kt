package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import java.util.Date

class OptimaMiData: AuditableEntity() {
    var employeeCode: String?= null
    var materialCode: String?= null
    var batchNo: String?= null
    var month: String?= null
    var year: String?= null
    var qtyDispatched: Int?= null
    var qtyValidated: Int?= null
    var qtyTransfered: Int?= null
    var qtyBalance: Int?= null
    var expiryDate: Date?= null
    var isBlockff: Int?= null
}
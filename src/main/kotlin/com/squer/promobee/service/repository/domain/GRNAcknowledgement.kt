package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.NamedSquerId
import java.util.Date

class GRNAcknowledgement: AuditableEntity() {
    var transactionNo: String?= null
    var poNo: String?= null
    var ccmId: NamedSquerEntity?= null
    var limid: String?= null
    var batchNo: String?= null
    var itemName: String?= null
    var postingDate: Date?= null
    var qty: Int?= null
    var value: Double?= null
    var vendorCode: String?= null
    var vendorName: String?= null
    var ratePerUnit: Double?= null
    var lineText: String?= null
    var isAcknowledged: Int?= null
    var onDate: Date?= null
    var remarks: String?= null
    var expiryDate: Date?= null
    var category: NamedSquerEntity?= null
    var hsnCode: String?= null
    var ratePerGRN: Double?= null
    var costCenter: String?= null
    var costCenterCode: String?= null
}
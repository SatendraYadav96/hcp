package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import java.math.BigInteger
import java.util.Date


class PoManual: AuditableEntity() {
    var poNo: BigInteger?= null
    var requestedDate: Date?= null
    var categoryId: NamedSquerEntity?= null
    var vendorId: NamedSquerEntity?= null
    var ccmId: NamedSquerEntity?= null
    var itemName: String?= null
    var itemDescription: String?= null
    var qty: Int?= null
    var value: Double?= null
    var userId: NamedSquerEntity?= null
    var statusId: NamedSquerEntity?= null
    var isDeleted: Int?= null
    var medicalCode: String?= null
}
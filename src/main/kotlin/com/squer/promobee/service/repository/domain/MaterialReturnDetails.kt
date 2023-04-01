package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import java.util.Date

class MaterialReturnDetails: AuditableEntity() {
    var omidId: OptimaMiData?= null
    var employeeCode: String?= null
    var month: Int?= null
    var year: Int?= null
    var itemCode: String?= null
    var batchNo: String?= null
    var balance: Int?= null
    var currierNo:String?= null
    var remark: String?= null
    var reason: String?= null
    var isAck: Int?= null
    var isSentTo: Int?= null
    var team: String?= null
    var itemName: String?= null
    var expiryDate: Date?= null
    var qtySent: Int?= null
    var diffQtyReason: String?= null
    var courierName: String?= null
    var dispatchDate:Date?= null
    var podNumCourier: String?= null
    var isDistroy: Int?= null
}
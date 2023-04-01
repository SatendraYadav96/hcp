package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class MaterialDetails: AuditableEntity(){
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
    var cfaRemark: String?= null
    var isAck: Int?= null
}
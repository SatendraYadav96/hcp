package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.User
import java.util.Date

class DispatchPlan: AuditableEntity() {
    var owner: NamedSquerEntity?= null
    var month: Int?= null
    var year: Int?= null
    var planStatus: NamedSquerEntity?= null
    var submissionDate: Date?= null
    var approvalDate: Date?= null
    var isSpecial: Int?= null
    var remarks: String?= null
    var invoiceStatus: NamedSquerEntity?= null
    var quarterlyPlan: String?= null
    var isVirtual: Int?= null
}
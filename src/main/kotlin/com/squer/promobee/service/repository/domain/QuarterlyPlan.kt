package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.User
import java.util.Date

class QuarterlyPlan: AuditableEntity() {
    var fromDate: Date?= null
    var toDate: Date?= null
    var owner: NamedSquerEntity?= null
    var planStatus: NamedSquerEntity?= null
}
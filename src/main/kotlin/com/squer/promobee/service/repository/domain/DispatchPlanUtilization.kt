package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class DispatchPlanUtilization: AuditableEntity() {
    var dipId: DispatchPlan?= null
    var month: String?= null
}
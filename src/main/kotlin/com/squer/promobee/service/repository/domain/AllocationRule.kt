package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class AllocationRule: AuditableEntity() {
    var designationId: NamedSquerEntity?= null
    var ownerStatus: NamedSquerEntity?= null
    var unitAllocation: Int?= null
    var categoryId: NamedSquerEntity?= null
}
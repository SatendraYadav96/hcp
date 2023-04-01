package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.User

class DispatchPlanBrand: AuditableEntity() {
    var dipId:DispatchPlan?= null
    var brdId: NamedSquerEntity?= null
    var dipOwnerId: NamedSquerEntity?= null
}
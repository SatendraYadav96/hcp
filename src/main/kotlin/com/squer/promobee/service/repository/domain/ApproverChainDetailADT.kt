package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class ApproverChainDetailADT: AuditableEntity() {
    var adfID: ApprovalChainDefinitionADF?= null
    var level: Int?= null
    var daysInQueue: Int?= null
    var rolId: NamedSquerEntity?= null
    var isRequired: Int?= null
}
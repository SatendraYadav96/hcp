package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class ApprovalChainDefinitionAPD: AuditableEntity() {
    var statusType: NamedSquerEntity?= null
    var srNo: Int?= null
    var rolId: String?= null
}
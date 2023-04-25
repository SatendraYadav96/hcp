package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class HSN: AuditableEntity() {


    var hcmCode: String?= null
    var rate: Double?= null
}
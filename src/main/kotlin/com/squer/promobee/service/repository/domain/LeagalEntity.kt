package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class LegalEntity: AuditableEntity() {
    var name: String?= null
    var ciName: String?  =null
    var code: String? = null
    var address: String?  = null
    var active: Int ?= null
}

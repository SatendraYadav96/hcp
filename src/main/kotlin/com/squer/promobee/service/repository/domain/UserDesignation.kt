package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class UserDesignation: AuditableEntity() {
    var name: String?= null
    var ciName: String?  =null
    var type: String?= null
    var displayOrder : Int?= null
}

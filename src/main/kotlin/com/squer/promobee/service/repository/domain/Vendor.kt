package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class Vendor: AuditableEntity() {
    var name: String?= null
    var ciName: String?= null
    var code: String?= null
    var addressLine1: String?= null
    var addressLine2: String?= null
    var city: String?= null
    var state: String?= null
    var zip: String?= null
    var active: Int?= null

}
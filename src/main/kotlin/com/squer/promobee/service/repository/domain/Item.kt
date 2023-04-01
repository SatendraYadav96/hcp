package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class Item: AuditableEntity() {
    var name: String?= null
    var ciName: String?= null
    var code: String?= null
    var category: NamedSquerEntity?= null
    var description: String?= null
    var medicalCode: String?= null
    var ccmId: NamedSquerEntity?= null
    var active: Int?= null
    var hsnCode: String?= null
}
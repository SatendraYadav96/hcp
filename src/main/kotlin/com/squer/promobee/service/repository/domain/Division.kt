package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class Division: AuditableEntity() {
    var name:String?= null
    var ciName: String?= null
    var bu: NamedSquerEntity?= null
    var code: String?= null
    var active: Int?= null
}